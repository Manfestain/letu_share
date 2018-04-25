package com.letu.share.Util;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

// 七牛上传文件工具类
public class QiniuUtil {
    PropertyUtil propertyUtil = new PropertyUtil();

    private String bucketName = propertyUtil.getProperty("bucketName");
    private String domain = propertyUtil.getProperty("domain");
    private String ACCESS_KEY = propertyUtil.getProperty("ACCESS_KEY");
    private String SECRET_KEY = propertyUtil.getProperty("SECRET_KEY");

    private Configuration configuration = new Configuration(Zone.zone0());
    private UploadManager uploadManager = new UploadManager(configuration);

    // 获取上传凭证
    private String getUpToken() {
        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
        String upToken = auth.uploadToken(bucketName);
        return upToken;
    }

    // 字节数组上传
    public Map<String, String> uploadFile(String content, String fileExtension) {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(content.getBytes());
        String key = UUID.randomUUID().toString().replaceAll("-", "") + fileExtension;
        Map<String, String> map = new HashMap<String, String>();

        String upToken = getUpToken();

        try {
            byte[] uploadBytes = content.getBytes("utf-8");
            try {
                Response response = uploadManager.put(uploadBytes, key, upToken);
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                map.put("key", putRet.key);
                map.put("domain", domain);
            } catch (QiniuException ex) {
                Response r = ex.response;
                System.out.println(r.toString());
                map.put("msg", toString().toString());
            }
        } catch (UnsupportedEncodingException ex) {
            System.out.println("不支持的编码格式");
            map.put("msg", "不支持的编码格式");
        } finally {
            return map;
        }
    }

    // 文件上传
    public Map<String, String> uploadFile(File file, String fileExtension) {
        Map<String, String> map = new HashMap<String, String>();
        String key = UUID.randomUUID().toString().replaceAll("-", "") + fileExtension;
        String upToken = getUpToken();

        try {
            Response response = uploadManager.put(file, key, upToken);
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            map.put("key", putRet.key);
            map.put("domain", domain);
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.out.println(r.toString());
            map.put("msg", toString().toString());
        } finally {
        return map;
        }
    }

    // 删除空间中的文件
    public Map<String, String> deleteFile(String key) {
        Map<String, String> map = new HashMap<String, String>();
        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
        BucketManager bucketManager = new BucketManager(auth, configuration);

        try {
            bucketManager.delete(bucketName, key);
        } catch (QiniuException ex) {
            map.put("msg", ex.response.toString());
        } finally {
            return map;
        }
    }
}
