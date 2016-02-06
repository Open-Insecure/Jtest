package com.br.dong.pinyin4j;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * Created with IntelliJ IDEA.
 * User: hexor
 * Date: 2016-02-01
 * Time: 14:44
 */
public class TestPinYin {
    private String[] pinyin;
    public static void main(String[] args) {

        String[] pinyin = PinyinHelper.toHanyuPinyinStringArray('重');
        System.out.println(pinyin[0]);
        TestPinYin pin=new TestPinYin();
        System.out.println(pin.getStringPinYin("容量统计"));
    }

    //转换一个字符串
    public String getStringPinYin(String str)
    {
        StringBuilder sb = new StringBuilder();
        String tempPinyin = null;
        for(int i = 0; i < str.length(); ++i)
        {
            tempPinyin =getCharacterPinYin(str.charAt(i));
            if(tempPinyin == null)
            {
                // 如果str.charAt(i)非汉字，则保持原样
                sb.append(str.charAt(i));
            }
            else
            {
                sb.append(tempPinyin);
            }
        }
        return sb.toString();
    }
    //转换单个字符
    public String getCharacterPinYin(char c)
    {
        try
        {
            HanyuPinyinOutputFormat format = null;
            format = new HanyuPinyinOutputFormat();
            format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
            pinyin = PinyinHelper.toHanyuPinyinStringArray(c, format);
        }
        catch(BadHanyuPinyinOutputFormatCombination e)
        {
            e.printStackTrace();
        }

        // 如果c不是汉字，toHanyuPinyinStringArray会返回null
        if(pinyin == null) return null;

        // 只取一个发音，如果是多音字，仅取第一个发音
        return pinyin[0];
    }
}
