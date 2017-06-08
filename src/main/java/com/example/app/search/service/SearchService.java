package com.example.app.search.service;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.app.search.Order;
import com.example.app.search.Query;
import com.example.app.search.entity.Issue;
import com.example.core.data.domain.TransitablePageImpl;
import com.example.core.helper.QueryBuilder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@ConfigurationProperties(prefix = "api")
public class SearchService {

    @Autowired
    RestOperations rest;

    @Value("${api.github.search.issues}")
    String api;

    public Page<Issue> getIssues(Query q, Order o, Pageable pageable) {

        QueryBuilder queries = QueryBuilder.from(q);

        if (queries.isEmpty()) {
            log.debug("queries is empty, skip rest operation.");
            return new PageImpl<Issue>(null, null, 0);
        }

        URI uri = UriComponentsBuilder.fromUriString(api)
                .queryParam("q", queries.build())
                .queryParam("sort", o.getSort())
                .queryParam("order", o.getOrder())
                .queryParam("page", pageable.getPageNumber() + 1)
                .queryParam("per_page", pageable.getPageSize())
                .build()
                .toUri();
        log.debug("rest operation for uri [{}]", uri.toString());

        ResponseEntity<Issues> entity = rest.getForEntity(uri, Issues.class);
        Issues body = entity.getBody();

        return new TransitablePageImpl<Issue>(body.getItems(), pageable, body.getTotalCount());
    }
}
