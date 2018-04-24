package com.letu.share.Util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

// 解析qiniu_info.properties文件
public class PropertyUtil {
    private static Properties properties;
    static {
        loadProps();
    }

    synchronized static private void loadProps() {
        String propertiesName = "qiniu_info.properties";
        properties  = new Properties();
        InputStream inputStream = null;
        try {
            // 使用类加载器获取文件流
            inputStream = PropertyUtil.class.getClassLoader().getResourceAsStream(propertiesName);
            properties.load(inputStream);
        } catch (IOException e) {
            System.out.println("读取出现异常");
        } finally {
            try {
                if (inputStream != null){
                    inputStream.close();
                }
            } catch (IOException e){
                System.out.println("关闭文件出现异常");
            }

        }
    }

    public static String getProperty(String key) {
        if (properties != null) {
            loadProps();
        }
        return properties.getProperty(key);
    }

    public static String getProperty(String key, String defaultValue) {
        if (properties != null)  {
            loadProps();
        }
        return properties.getProperty(key, defaultValue);
    }
}
