package com.oraro.panoramicagriculture.utils;

import android.text.TextUtils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.regex.Pattern;

/**
 * Created by boobooL on 2016/4/6 0006
 * Created 邮箱 ：boobooMX@163.com
 */
public class PinyinUtils {

    private static String s;

    /**
     * 获取拼音的首字母(大写)
     * @param pinyin
     * @return
     */
    public static String getFirstLetter(final String pinyin){
        String c=pinyin.substring(0,1);
        Pattern pattern=Pattern.compile("^[A-Za-z]+$");
        if(pattern.matcher(c).matches()){
            s = c.toUpperCase();
        }
        return  s;
    }
    public static String converterToFirstSpell(String chines) {
        String pinyinName = "";
//        char[] nameChar = chines.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        try {
            pinyinName  =PinyinHelper.toHanYuPinyinString(chines,defaultFormat,"",false);
        } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
            badHanyuPinyinOutputFormatCombination.printStackTrace();
        }
//        for (int i = 0; i < nameChar.length; i++) {
//            if (nameChar[i] > 128) {
//                try {
//                    pinyinName += PinyinHelper.toHanyuPinyinStringArray(
//                            nameChar[i], defaultFormat)[0].charAt(0);
//
//                } catch (BadHanyuPinyinOutputFormatCombination e) {
//                    e.printStackTrace();
//                }
//            } else {
//                pinyinName += nameChar[i];
//            }
//        }
        return pinyinName;


    }
}
