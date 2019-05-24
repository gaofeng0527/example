package com.spring.utils;

import org.apache.commons.io.FilenameUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.util.StringUtils;

public class ImageUrlUtil {

    public static void main(String[] args) {
        //String context = "<p>已知极限 <img src=\"upload/file/28281/title?__id=xRfhqpHWrvntsqSyhCZH.gif\" width=\"127\" height=\"46\" align=\"absmiddle\" />，求常数<img src=\"upload/file/28282/title?__id=44ifjZDUaWvb7gK4Jeb5.gif\" width=\"14\" height=\"21\" align=\"absmiddle\" />的值.</p>";
        //replaceImageUrlToBase64(context);
        String str = "/exam-admin/home/my/admin/questionbank/question/attaches/upload/file/22049/title?__id=7ePlhPVXVadQC1heCOrG.png";
        System.out.println(str.substring(str.indexOf("upload"),str.length()));
    }

    public static String replaceImageUrlToBase64(String context,String url,String cookie) {
        Document document = Jsoup.parse(context);
        Elements images = document.select("img[src]");
        for (Element img : images) {
            String src = img.attr("src");
            if(src.indexOf(",") > 0){
                src = src.split(",")[0];
            }

            if(src.indexOf("/home/my/admin/module") > 0 || src.indexOf("home/my/admin/") > 0){
                src = src.substring(src.indexOf("upload"),src.length());
            }

            if (StringUtils.hasText(src) && src.indexOf("upload/file/") >= 0) {
                String fix = FilenameUtils.getExtension(src).toLowerCase();
                if(StringUtils.hasText(fix) && fix.equals("mp3")){
                    System.out.println(fix);
                    continue;
                }
                img.attr("src", ImageBase64Util.imageToBase64ByUrl(url, src, cookie));
            }
        }
        return document.body().html();
    }
}
