package com.peak.controller;

import com.peak.utils.ImageBase64Util;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class TinyMCEController {

    @Value("${upload.file.dir}")
    private String uploadDir;

    @GetMapping("/upload-file")
    public String uploadFilePage(){
        return "tinyMCE";
    }

    @PostMapping("/upload-file")
    @ResponseBody
    public Map<String,String> uploadFile(@RequestParam("file")MultipartFile file){
        Map<String,String> result = new HashMap<>();
        String fileName = saveFile(file);
        if(!StringUtils.hasText(fileName)){
            result.put("message","图片上传失败");
        }
        String base64Str = "";
        try {
            base64Str = ImageBase64Util.imageToBase64String(fileName);
        } catch (IOException e) {
            result.put("message",e.getMessage());
            e.printStackTrace();
            return result;
        }

        result.put("location",base64Str);
        return result;
    }

    @PostMapping("/upload")
    @ResponseBody
    public Map<String,String> upload(@RequestParam("file")MultipartFile file){
        Map<String,String> result = new HashMap<>();
        String fileName = saveFile(file);
        if(!StringUtils.hasText(fileName)){
            result.put("message","图片上传失败");
        }
        result.put("location",fileName);
        return result;
    }

    /**
     * 把 HTTP 请求中的文件流保存到本地
     *
     * @param file MultipartFile 的对象
     */
    private String saveFile(MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                String path = uploadDir + File.separator + file.getOriginalFilename();
                FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(path));

                return path;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
