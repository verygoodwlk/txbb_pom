package com.qf.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * @Author ken
 * @Date 2019/2/18
 * @Version 1.0
 */
public class PinyinUtils {

    /**
     * 将string字符串转换成拼音
     * @param str
     * @return
     */
    public static String str2Pinyin(String str){

        if(str == null || str.trim().equals("")){
            return null;
        }

        HanyuPinyinOutputFormat outputFormat = new HanyuPinyinOutputFormat();
        //设置声调
        outputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);

        StringBuilder sb = new StringBuilder();

        //将字符串转换成字符数组
        char[] chars = str.toCharArray();

        for (char aChar : chars) {
            //循环依次将字符转换成拼音
            try {
                String[] strs =
                        PinyinHelper.toHanyuPinyinStringArray(aChar, outputFormat);

                if(strs != null){
                    sb.append(strs[0]);
                } else {
//                    sb.append(aChar);
                }
            } catch (BadHanyuPinyinOutputFormatCombination e) {
                e.printStackTrace();
            }
        }

        return sb.toString();
    }

    public static void main(String[] args) throws BadHanyuPinyinOutputFormatCombination {
//        HanyuPinyinOutputFormat outputFormat = new HanyuPinyinOutputFormat();
//        //设置声调
//        outputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
//
//        String[] strs =
//                PinyinHelper.toHanyuPinyinStringArray('#', outputFormat);
//        System.out.println(Arrays.toString(strs));

        String result = str2Pinyin("白#日12312依@山！@#！@尽");
        System.out.println(result);
    }
}
