package com.example.core.helper;

import java.util.Arrays;
import java.util.StringJoiner;

import javax.servlet.http.HttpServletResponse;

import org.springframework.util.ReflectionUtils;
import org.springframework.web.util.CookieGenerator;

public class CookieHelper extends CookieGenerator {

    public static void addCookie(HttpServletResponse response, String name, String value) {
        CookieGenerator generator = new CookieGenerator();
        generator.setCookieName(name);
        generator.addCookie(response, value);
    }

    public static void removeCookie(HttpServletResponse response, String name) {
        CookieGenerator generator = new CookieGenerator();
        generator.setCookieName(name);
        generator.removeCookie(response);
    }

    public static void addCookies(HttpServletResponse response, String prefix, Object obj) {
        Arrays.stream(obj.getClass().getDeclaredFields()).forEach(field -> {
            ReflectionUtils.makeAccessible(field);
            addCookie(response,
                    new StringJoiner(".").add(prefix).add(field.getName()).toString(),
                    ReflectionUtils.getField(field, obj).toString());
        });
    }

    public static void removeCookies(HttpServletResponse response, String prefix, Object obj) {
        Arrays.stream(obj.getClass().getDeclaredFields()).forEach(field -> {
            removeCookie(response, new StringJoiner(".").add(prefix).add(field.getName()).toString());
        });
    }

}
