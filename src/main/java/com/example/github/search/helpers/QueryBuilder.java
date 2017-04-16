package com.example.github.search.helpers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.util.ObjectUtils;

public class QueryBuilder {

    private static final String QUERY_DELIMITER = "+";
    private static final String KEY_VALUE_DELIMITER = ":";

    private List<String> queries = new ArrayList<String>();

    public QueryBuilder() {
    }

    public QueryBuilder(Object query) {
        Arrays.stream(query.getClass().getDeclaredFields()).forEach(field -> {
            // get query parameter(s)
            Object value = null;
            try {
                field.setAccessible(true);
                value = field.get(query);
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }

            // set query parameter(s)
            if (!ObjectUtils.isEmpty(value)) {
                if (ObjectUtils.isArray(value)) {
                    addParameters(field.getName(), (Object[]) value);
                } else {
                    addParameter(field.getName(), value);
                }
            }
        });
    }

    public QueryBuilder addParameter(String query) {
        queries.add(query);
        return this;
    }

    public QueryBuilder addParameter(String query, Object value) {
        queries.add(query + KEY_VALUE_DELIMITER + value.toString());
        return this;
    }

    public QueryBuilder addParameters(String query, Object[] values) {
        Stream.of(values).forEach(value -> {
            queries.add(query + KEY_VALUE_DELIMITER + value.toString());
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
