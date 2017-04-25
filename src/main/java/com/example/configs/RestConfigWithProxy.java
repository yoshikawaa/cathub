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
import org.springframework.web.client.RestTemplate;

@Profile("proxy")
@Configuration
@ConfigurationProperties(prefix = "proxy")
public class RestConfigWithProxy {

    @Bean
    public HttpClientBuilder proxyHttpClientBuilder(@Value("${proxy.host}") String host,
            @Value("${proxy.port}") int port,
            @Value("${proxy.user}") String user,
            @Value("${proxy.password}") String password) {
        HttpClientBuilder builder = HttpClientBuilder.create();

        // set credential provider
        BasicCredentialsProvider provider = new BasicCredentialsProvider();
        provider.setCredentials(new AuthScope(host, port), new UsernamePasswordCredentials(user, password));
        builder.setDefaultCredentialsProvider(provider);

        // set proxy
        builder.setProxy(new HttpHost(host, port));

        return builder;
    }

    @Bean
    public RestTemplate restTemplate(HttpClientBuilder builder) {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(builder.build());
        return new RestTemplate(factory);
    }
}
