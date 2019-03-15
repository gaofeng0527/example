package com.util;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringTest {

    public static void main(String[] args) {
        String str = "<body lang=ZH-CN style='text-justify-trim:punctuation'><div class=WordSection1 style='layout-grid:15.6pt'><p class=MsoNormal><span style='font-family:宋体'>模拟试卷一</span></p><h2><span style='font-family:宋体'>单选题</span><a name=\"_GoBack\"></a></h2><p class=MsoNormal><span lang=EN-US>A.<img width=16 height=20\n" +
                "src=\"zt_00010_1604.files/image003.png\"></span></p></div></body>";
        System.out.println(removeEleProp(str));
    }


    private static final String regEx_tag = "<(\\w[^>|\\s]*)[\\s\\S]*?>";

    public static String removeEleProp(String htmlStr) {
        Pattern p = Pattern.compile(regEx_tag, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(htmlStr);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            String tagWithProp= m.group(0);
            String tag =m.group(1);
            if ("img".equals(tag)) {
                //img标签保留属性，可进一步处理删除无用属性，仅保留src等必要属性
                m.appendReplacement(sb, tagWithProp);
            }else if ("a".equals(tag)) {
                //a标签保留属性，可进一步处理删除无用属性，仅保留href等必要属性
                m.appendReplacement(sb, tagWithProp);
            }else{
                m.appendReplacement(sb, "<" + tag + ">");
            }
        }
        m.appendTail(sb);
        return sb.toString();
    }
    
}
