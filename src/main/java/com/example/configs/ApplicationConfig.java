package com.example.configs;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.ApplicationScope;

@Configuration
public class ApplicationConfig {
    @ApplicationScope
    @Bean("type")
    public List<String> type() {
        return Arrays.asList("issue", "pr");
    }
    @ApplicationScope
    @Bean("is")
    public List<String> is() {
        return Arrays.asList("open", "closed", "merged");
    }
    @ApplicationScope
    @Bean("in")
    public List<String> in() {
        return Arrays.asList("title", "body", "comments");
    }
    @ApplicationScope
    @Bean("sort")
    public List<String> sort() {
        return Arrays.asList("comments", "created", "updated");
    }
    @ApplicationScope
    @Bean("order")
    public List<String> order() {
        return Arrays.asList("asc", "desc");
    }
}
