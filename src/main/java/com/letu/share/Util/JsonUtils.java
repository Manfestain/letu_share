package com.letu.share.Util;

import com.alibaba.fastjson.JSONObject;

public class JsonUtils {

    public static Object objectToBase64(Object object) {
        String jsonString = JSONObject.toJSONString(object);
        byte[] convert = java.util.Base64.getEncoder().encode(jsonString.getBytes());
        return convert;
    }

}
