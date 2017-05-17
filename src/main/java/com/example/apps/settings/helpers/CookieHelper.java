package com.example.apps.settings.helpers;

import java.util.Arrays;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.util.CookieGenerator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
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
            try {
                field.setAccessible(true);
                addCookie(response, prefix + "." + field.getName(), field.get(obj).toString());
            } catch (IllegalAccessException | IllegalArgumentException e) {
                log.trace("building cookie is failed.", e);
            }
        });
    }

    public static void removeCookies(HttpServletResponse response, String prefix, Object obj) {
        Arrays.stream(obj.getClass().getDeclaredFields()).forEach(field -> {
            removeCookie(response, prefix + "." + field.getName());
        });
    }

}
