package com.example.apps.search.helpers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.example.apps.search.helpers.annotation.QueryParam;
import com.example.core.exception.SystemException;

public class QueryBuilder {

    private static final String QUERY_DELIMITER = "+";
    private static final String KEY_VALUE_DELIMITER = ":";

    private final List<String> queries = new ArrayList<String>();

    public static QueryBuilder from(Object obj) {
        QueryBuilder builder = new QueryBuilder();
        Arrays.stream(obj.getClass().getDeclaredFields()).forEach(field -> {
            // get query parameter(s)
            QueryParam query = field.getDeclaredAnnotation(QueryParam.class);
            String name = (query != null && !StringUtils.isEmpty(query.name())) ? query.name() : field.getName();
            Object value = null;
            try {
                field.setAccessible(true);
                value = field.get(obj);
            } catch (IllegalAccessException | IllegalArgumentException e) {
                throw new SystemException("failed to build query.", e);
            }

            // ignore empty parameter(s)
            if (ObjectUtils.isEmpty(value) || (ObjectUtils.isArray(value) && ObjectUtils.isEmpty((Object[]) value))) {
                return;
            }

            // set query parameter(s)
            if (ObjectUtils.isArray(value)) {
                Object[] values = (Object[]) value;
                if (query != null && !query.requiredValue()) {
                    builder.addParameters(values);
                } else {
                    builder.addParameters(name, values);
                }
            } else {
                if (query != null && !query.requiredValue()) {
                    builder.addParameter(value);
                } else {
                    builder.addParameter(name, value);
                }
            }
        });
        return builder;
    }

    public QueryBuilder addParameter(Object name) {
        queries.add(name.toString());
        return this;
    }

    public QueryBuilder addParameter(String name, Object value) {
        queries.add(name + KEY_VALUE_DELIMITER + value.toString());
        return this;
    }

    public QueryBuilder addParameters(Object[] names) {
        Stream.of(names).forEach(name -> {
            addParameter(name);
        });
        return this;
    }

    public QueryBuilder addParameters(String name, Object[] values) {
        Stream.of(values).forEach(value -> {
            addParameter(name, value);
        });
        return this;
    }

    public Boolean isEmpty() {
        return queries.isEmpty();
    }

    public String build() {
        return queries.stream().collect(Collectors.joining(QUERY_DELIMITER));
    }
}
