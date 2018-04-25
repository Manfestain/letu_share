package com.letu.share.Util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

// MultipartFile工具类
public class MultipartFileUtil {

    // 转为File
    public File multipartFiletoFile(MultipartFile multipartFile, String prefix) {
        try {
            final File tempFile = File.createTempFile(UUID.randomUUID().toString().replaceAll("-", ""), prefix);
            multipartFile.transferTo(tempFile);
            return tempFile;
        } catch (IOException e) {
            System.out.println("创建临时文件出错");
            return null;
        }
    }

    // 删除临时文件
    public void deleteFile(File... files) {
        for (File file : files) {
            if (file.exists()) {
                file.delete();
            }
        }
    }

}
