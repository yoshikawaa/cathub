package com.example.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.example.core.thymeleaf.dialect.AdditionalDialect;
import com.example.core.thymeleaf.expression.Eachs;
import com.example.core.thymeleaf.expression.Maths;
import com.google.common.collect.ImmutableMap;

@Configuration
@EnableWebMvc
@ConfigurationProperties(prefix = "app")
public class WebConfig extends WebMvcConfigurerAdapter {

    @Value("${app.data.commons.default-page-size}")
    private int size;
    
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        PageableHandlerMethodArgumentResolver resolver = new PageableHandlerMethodArgumentResolver();
        resolver.setFallbackPageable(new PageRequest(0, size));
        argumentResolvers.add(resolver);
        super.addArgumentResolvers(argumentResolvers);        
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }

    @Bean
    public AdditionalDialect additionalDialect() {
        return new AdditionalDialect(ImmutableMap.of("eachs", new Eachs(), "maths", new Maths()));
    }
    
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("views/home");
    }

}
