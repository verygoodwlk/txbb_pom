package com.qf.websocket;

import com.qf.websocket.handler.ChatWebSocketHandler;
import com.qf.websocket.handler.ConnWebSocketHandler;
import com.qf.websocket.handler.HeartWebSocketHandler;
import com.qf.websocket.handler.TextWebSocketHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 *
 * WebSocket服务类
 *
 * @Author ken
 * @Date 2019/2/21
 * @Version 1.0
 */
@Component
public class WebSocketServer implements CommandLineRunner {

    private static final EventLoopGroup masterGroup = new NioEventLoopGroup();
    private static final EventLoopGroup slaveGroup = new NioEventLoopGroup();

    @Value("${ws.ip}")
    private String ip;

    @Value("${ws.port}")
    private int port;

    @Value("${zk.host}")
    private String zkHost;

    //服务器的Channel对象
    private Channel channel;

    @Autowired
    private TextWebSocketHandler textWebSocketHandler;

    @Autowired
    private ConnWebSocketHandler connWebSocketHandler;

    @Autowired
    private HeartWebSocketHandler heartWebSocketHandler;

    @Autowired
    private ChatWebSocketHandler chatWebSocketHandler;

    /**
     * 初始化WebSocket服务器
     */
    private ChannelFuture init(){
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap
                .group(masterGroup, slaveGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer() {

                    @Override
                    protected void initChannel(Channel channel) throws Exception {
                        ChannelPipeline pipeline = channel.pipeline();

                        pipeline.addLast(new HttpServerCodec());
                        pipeline.addLast(new HttpObjectAggregator(512 * 1024));
                        //处理Http的升级握手，并且处理所有的控制帧（Ping、Pong、Close）
                        pipeline.addLast(new WebSocketServerProtocolHandler("/txbb"));
                        //添加一个读超时的处理器, 在10秒钟之内，如果当前这个客户端（channel），没有读取到任何内容，则自动关闭
                        pipeline.addLast(new ReadTimeoutHandler(10, TimeUnit.SECONDS));
                        //自定义WebSocket的文本帧处理器
                        pipeline.addLast(textWebSocketHandler);//验证消息格式
                        pipeline.addLast(connWebSocketHandler);//处理连接握手
                        pipeline.addLast(heartWebSocketHandler);//处理心跳消息
                        pipeline.addLast(chatWebSocketHandler);//处理单聊消息
                    }
                });

        ChannelFuture future = serverBootstrap.bind(port);
        future.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if(channelFuture.isSuccess()){
                    //绑定9000端口成功
                    System.out.println("WebSocket服务已经启动，端口为：" + port);
                    channel = future.channel();

                    //------注册Zookeeper------
                    connZookeeper();
                } else {
                    //绑定9000端口失败
                    //优雅关闭Netty服务
                    destory();
                    //失败的原因打印到控制台
                    Throwable e = channelFuture.cause();
                    e.printStackTrace();
                }
            }
        });

        return future;
    }

    /**
     * 注销Netty服务
     */
    private void destory(){
        if(channel != null && channel.isActive()){
            channel.close();
        }
        //优雅关闭主从线程组
        masterGroup.shutdownGracefully();
        slaveGroup.shutdownGracefully();
    }


    private void connZookeeper(){
        try {
            //连接Zookeeper
            ZooKeeper zooKeeper = new ZooKeeper(zkHost, 60, new Watcher() {
                @Override
                public void process(WatchedEvent watchedEvent) {

                }
            });

            //在根路径下创建一个Netty节点
            Stat stat = zooKeeper.exists("/netty", null);
            if(stat == null){
                zooKeeper.create(
                        "/netty",
                        null,
                        ZooDefs.Ids.OPEN_ACL_UNSAFE,
                        CreateMode.PERSISTENT);
            }

            //创建当前服务器所对应的临时节点
            //节点的值 ： ip:port
            String value = ip + ":" + port;
            String str = zooKeeper.create("/netty/server", value.getBytes("utf-8"), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            System.out.println("服务器在zookeeper上创建了一个临时节点：" + str);

        } catch (Exception e) {
            e.printStackTrace();
            destory();
        }
    }

    @Override
    public void run(String... args) throws Exception {
        ChannelFuture future = init();

        //设置一个运行时的销毁回调
        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                destory();
            }
        });

        //同步阻塞
        future.channel().closeFuture().syncUninterruptibly();
    }
}
