package com.example.core.thymeleaf.expression;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.ObjectUtils;

import com.example.core.exception.SystemException;

public class Eachable {

    public Object[] asArray(Object obj) {
        return this.asList(obj).toArray();
    }

    public List<Object> asList(Object obj) {
        List<Object> list = new ArrayList<Object>();
        Arrays.stream(obj.getClass().getDeclaredFields()).forEach(field -> {
            Object value = null;
            try {
                field.setAccessible(true);
                value = field.get(obj);
            } catch (IllegalAccessException | IllegalArgumentException e) {
                throw new SystemException("failed to convert as list.", e);
            }

            if (ObjectUtils.isArray(value)) {
                Arrays.stream((Object[]) value).forEach(v -> {
                    list.add(v);
                });
            } else {
                list.add(value);
            }
        });
        return list;
    }

    public Map<String, Object> asMap(Object obj) {
        Map<String, Object> map = new HashMap<String, Object>();
        Arrays.stream(obj.getClass().getDeclaredFields()).forEach(field -> {
            Object value = null;
            try {
                field.setAccessible(true);
                value = field.get(obj);
            } catch (IllegalAccessException | IllegalArgumentException e) {
                throw new SystemException("failed to convert as map.", e);
            }

            if (ObjectUtils.isArray(value)) {
                Arrays.stream((Object[]) value).forEach(v -> {
                    map.put(field.getName(), v);
                });
            } else {
                map.put(field.getName(), value);
            }
        });
        return map;
    }

}
