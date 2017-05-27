package com.example.configs;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.example.core.thymeleaf.dialect.SpringAdditionalDialect;
import com.example.core.thymeleaf.expression.Eachable;

@Configuration
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("views/home");
    }

    @Bean
    public SpringAdditionalDialect springAdditionalDialect() {
        Map<String, Object> expressionObjects = new HashMap<String, Object>();
        expressionObjects.put("eachable", new Eachable());
        return new SpringAdditionalDialect(expressionObjects);
    }
}
