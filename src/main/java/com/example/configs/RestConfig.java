package com.example.configs;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

public class RestConfig {

    @Profile("default")
    @Configuration
    public static class Default {
        @Bean
        public RestTemplate restTemplate() {
            return new RestTemplate();
        }
    }
    
    @Profile("proxy")
    @Configuration
    @ConfigurationProperties(prefix = "proxy")
    public static class Proxy {
        @Bean
        public HttpClientBuilder proxyHttpClientBuilder(@Value("${proxy.host}") String host,
                @Value("${proxy.port}") int port,
                @Value("${proxy.user}") String user,
                @Value("${proxy.password}") String password) {

            HttpClientBuilder builder = HttpClientBuilder.create();

            // set proxy
            builder.setProxy(new HttpHost(host, port));

            // set credential provider
            if (!StringUtils.isEmpty(user) && !StringUtils.isEmpty(password)) {
                BasicCredentialsProvider provider = new BasicCredentialsProvider();
                provider.setCredentials(new AuthScope(host, port), new UsernamePasswordCredentials(user, password));
                builder.setDefaultCredentialsProvider(provider);
            }

            return builder;
        }

        @Bean
        public RestTemplate restTemplate(HttpClientBuilder builder) {
            HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(builder.build());
            return new RestTemplate(factory);
        }
    }
}
