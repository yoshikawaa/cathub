package com.example.apps.search.helpers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.util.ObjectUtils;

import com.example.apps.search.helpers.annotation.QueryParam;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class QueryBuilder {

    private static final String QUERY_DELIMITER = "+";
    private static final String KEY_VALUE_DELIMITER = ":";

    private List<String> queries = new ArrayList<String>();

    public static QueryBuilder fromQuery(Object query) {
        QueryBuilder builder = new QueryBuilder();
        Arrays.stream(query.getClass().getDeclaredFields()).forEach(field -> {
            // get query parameter(s)
            QueryParam q = field.getAnnotation(QueryParam.class);
            Object value = null;
            try {
                field.setAccessible(true);
                value = field.get(query);
            } catch (IllegalArgumentException | IllegalAccessException e) {
                log.trace("building query is failed.", e);
            }

            // ignore empty parameter(s)
            if (ObjectUtils.isEmpty(value)) {
                return;
            }

            // set query parameter(s)
            if (ObjectUtils.isArray(value)) {
                Object[] values = (Object[]) value;
                if (q != null) {
                    if (q.requiredValue()) {
                        builder.addParameters(q.name(), values);
                    } else {
                        builder.addParameters(values);
                    }
                } else {
                    builder.addParameters(field.getName(), values);
                }
            } else {
                if (q != null) {
                    if (q.requiredValue()) {
                        builder.addParameter(q.name(), value);
                    } else {
                        builder.addParameter(value);
                    }
                } else {
                    builder.addParameter(field.getName(), value);
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
