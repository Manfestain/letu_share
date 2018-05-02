package com.letu.share.Util;

import redis.clients.jedis.Jedis;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

public class SessionUtils {
    private Jedis jedis = new Jedis();
    private int exp = 60*24*10;

    public static String getCookieSessionId(HttpServletResponse response, HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if ("CSESSIONID".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        String csessionId = UUID.randomUUID().toString().replaceAll("-", "");
        Cookie cookie = new Cookie("CSESSIONID", csessionId);
        cookie.setPath("/");
        cookie.setMaxAge(-1);
        return csessionId;
    }


    public String getAttributerForUsername(String csessionId) {
        String value = jedis.get(csessionId + ":USER_NAME");
        if (value != null) {
            jedis.expire(csessionId + ":USER_NAME", 60*exp);
            return value;
        }
        return null;
    }
}
