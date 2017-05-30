package com.example.core.thymeleaf.expression;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.ObjectUtils;
import org.springframework.util.ReflectionUtils;

public class Eachable {

    public Object[] asArray(Object obj) {
        return this.asList(obj).toArray();
    }

    public List<Object> asList(Object obj) {
        List<Object> list = new ArrayList<Object>();
        
        Arrays.stream(obj.getClass().getDeclaredFields()).forEach(field -> {
            ReflectionUtils.makeAccessible(field);
            addToList(list, ReflectionUtils.getField(field, obj));
        });
        return list;
    }

    public Map<String, Object> asMap(Object obj) {
        Map<String, Object> map = new HashMap<String, Object>();
        Arrays.stream(obj.getClass().getDeclaredFields()).forEach(field -> {
            ReflectionUtils.makeAccessible(field);
            putOnMap(map, field.getName(), ReflectionUtils.getField(field, obj));
        });
        return map;
    }
    
    private void addToList(List<Object> list, Object value) {
        if (ObjectUtils.isArray(value)) {
            Arrays.stream((Object[]) value).forEach(v -> {
                addToList(list, value);
            });
        } else {
            list.add(value);
        }
    }
    
    private void putOnMap(Map<String, Object> map, String name, Object value) {
        if (ObjectUtils.isArray(value)) {
            Arrays.stream((Object[]) value).forEach(v -> {
                putOnMap(map, name, v);
            });
        } else {
            map.put(name, value);
        }
    }

}
