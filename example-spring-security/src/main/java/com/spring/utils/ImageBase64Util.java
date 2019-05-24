package com.spring.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.util.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 图片转换为base64
 */
public class ImageBase64Util {

    public static void main(String[] args) throws IOException {
        String cookie = "JSESSIONID=9EEC4F4B23AD5329D7E6D6FE46E1758C; UM_distinctid=16624739e36108e-0452e83d0e143c-551e3f12-13c680-16624739e371ca; mantisId=87595516e5b24e0bb18bd965bdc41f9e; mantis7055=0569135e68124031b11f391d666e47ee@7055; Hm_lvt_8edc16894c9c51fbc1cdf95ce7e14a44=1546484510; CNZZDATA1261015652=1268210592-1538205349-null%7C1553063272; _tenant=default; Hm_lvt_c3f0b24e5f48f939831d8961d740e1c3=1550711988,1553063636; referer=\"http://www.edu-edu.com/\"; service=\"http://www.edu-edu.com/exam-admin/cas_security_check\"; 128_vq=15; Hm_lpvt_c3f0b24e5f48f939831d8961d740e1c3=1553067852";
        //String str = imageToBase64String("F:\\a\\image126.gif");
        //System.out.println(str);
        String strs = "image.mp3";
        System.out.println(FilenameUtils.getExtension(strs).toLowerCase());

        //saveBase64ImageStringToImage("/Users/Biao/Desktop", "y", str);
        //String str = imageToBase64ByUrl("http://www.edu-edu.com/exam-admin/home/my/admin/module/question/attaches/","upload/file/7486/title?__id=WzahXMerfbTqBHT5BKx7.png", cookie);
        //System.out.println(str);
    }

    /**
     * 图片转换成base64
     *
     * @param imagePath 图片路径
     * @return 转换后的字符串
     * @throws IOException
     */
    public static String imageToBase64String(String imagePath) throws IOException {
        // 图片的格式为 data:image/png;base64,iVBORw0KGgoAAAA...
        // 逗号的前面为图片的格式，逗号后们为图片二进制数据的 Base64 编码字符串
        String prefix = String.format("data:image/%s;base64,", FilenameUtils.getExtension(imagePath).toLowerCase());
        byte[] content = FileUtils.readFileToByteArray(new File(imagePath));
        return prefix + Base64.encodeBase64String(content);
    }

    /**
     * 把 Base64 编码的字符串表示的图片保存到传入的目录 directory 下，图片的名字为 baseName，不包含后缀，
     * 图片的后缀从 base64ImageString 中解析得到
     *
     * @param directory         要保存图片的目录
     * @param baseName          图片的名字
     * @param base64ImageString 图片的 Base64 编码的字符串
     * @throws IOException
     */
    public static void saveBase64ImageStringToImage(String directory, String baseName, String base64ImageString) throws IOException {
        // 图片的格式为 data:image/png;base64,iVBORw0KGgoAAAA...
        // 逗号的前面为图片的格式，逗号后们为图片二进制数据的 Base64 编码字符串
        int commaIndex = base64ImageString.indexOf(",");
        String extension = base64ImageString.substring(0, commaIndex).replaceAll("data:image/(.+);base64", "$1");
        byte[] content = Base64.decodeBase64(base64ImageString.substring(commaIndex));

        FileUtils.writeByteArrayToFile(new File(directory, baseName + "." + extension), content);
    }


    /**
     * 根据URL获取图片的base64编码
     *
     * @param imgUrl 图片url路径
     * @param cookie 有些时候，访问图片也是需要登录验证的，这个时候可以通过浏览器中获取cookie字符串，跟着请求发送到服务器端
     *               Cookie:JSESSIONID=9EEC4F4B23AD5329D7E6D6FE46E1758C; UM_distinctid=16624739e36108e-0452e83d0e143c-551e3f12-13c680-16624739e371ca; mantisId=87595516e5b24e0bb18bd965bdc41f9e; mantis7055=0569135e68124031b11f391d666e47ee@7055; Hm_lvt_8edc16894c9c51fbc1cdf95ce7e14a44=1546484510; CNZZDATA1261015652=1268210592-1538205349-null%7C1553063272; _tenant=default; Hm_lvt_c3f0b24e5f48f939831d8961d740e1c3=1550711988,1553063636; referer="http://www.edu-edu.com/"; service="http://www.edu-edu.com/exam-admin/cas_security_check"; 128_vq=15; Hm_lpvt_c3f0b24e5f48f939831d8961d740e1c3=1553067852
     * @return
     */
    public static String imageToBase64ByUrl(String imgUrl, String src, String cookie) {



        String prefix = String.format("data:image/%s;base64,", FilenameUtils.getExtension(imgUrl).toLowerCase());
        URL url;
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        ByteArrayOutputStream baos = null;
        try {
            url = new URL(imgUrl + src);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Cookie", cookie);

            urlConnection.connect();
            inputStream = urlConnection.getInputStream();

            baos = new ByteArrayOutputStream();

            byte[] buffer = new byte[1024];
            int len = 0;
            //使用一个输入流从buffer里把数据读取出来
            while ((len = inputStream.read(buffer)) != -1) {
                //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
                baos.write(buffer, 0, len);
            }
            // 对字节数组Base64编码
            return prefix + Base64.encodeBase64String(baos.toByteArray());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (baos != null) {
                try {
                    baos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        System.out.println(src);
        return src;

    }
}
