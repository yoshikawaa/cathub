package com.example.app.search.repositories.service;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.app.search.common.Order;
import com.example.app.search.common.service.AbstractSearchService;
import com.example.app.search.repositories.Query;
import com.example.app.search.repositories.entity.Repository;
import com.example.core.helper.QueryBuilder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@ConfigurationProperties(prefix = "api")
public class SearchRepositoriesService extends AbstractSearchService {

    @Value("${api.github.search.repositories}")
    String api;

    public List<Repository> getRepositories(Query q, Order o) {

        QueryBuilder queries = QueryBuilder.from(q);

        if (queries.isEmpty()) {
            log.debug("queries is empty, skip rest operation.");
            return null;
        }

        URI uri = UriComponentsBuilder.fromUriString(api)
                .queryParam("q", queries.build())
                .queryParam("sort", o.getSort())
                .queryParam("order", o.getOrder())
                .queryParam("per_page", size)
                .build()
                .toUri();

        log.debug("rest operation for uri [{}]", uri.toString());
        ResponseEntity<RepositoriesBody> entity = rest.getForEntity(uri, RepositoriesBody.class);

        return entity.getBody().getItems();
    }
}
