package com.example.app.search.service;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.domain.Page;
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

    @Value("${api.github.search.max-page-size}")
    int size;

    @Value("${api.github.search.limit-element-size}")
    int limit;

    public Page<Issue> getIssues(Query q, Order o, Pageable pageable) {

        QueryBuilder queries = QueryBuilder.from(q);

        if (queries.isEmpty()) {
            log.debug("queries is empty, skip rest operation.");
            return null;
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

        return new TransitablePageImpl<Issue>(body.getItems(), pageable, body.getTotalCount(), limit);
    }

    public List<Issue> getAllIssues(Query q, Order o) {

        QueryBuilder queries = QueryBuilder.from(q);

        if (queries.isEmpty()) {
            log.debug("queries is empty, skip rest operation.");
            return null;
        }

        List<Issue> issues = new ArrayList<Issue>();
        AtomicInteger page = new AtomicInteger(1);

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(api)
                .queryParam("q", queries.build())
                .queryParam("sort", o.getSort())
                .queryParam("order", o.getOrder())
                .queryParam("per_page", size);

        Issues body = this.getNextPage(builder, page);
        issues.addAll(body.getItems());

        while (issues.size() < Math.min(body.getTotalCount(), limit)) {
            body = this.getNextPage(builder, page);
            issues.addAll(body.getItems());
        }

        return issues;
    }

    private Issues getNextPage(UriComponentsBuilder builder, AtomicInteger page) {
        URI uri = builder.queryParam("page", page.getAndIncrement()).build().toUri();
        log.debug("rest operation for uri [{}]", uri.toString());
        return rest.getForEntity(uri, Issues.class).getBody();
    }

}
