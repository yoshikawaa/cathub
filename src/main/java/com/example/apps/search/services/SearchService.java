package com.example.apps.search.services;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.apps.search.Order;
import com.example.apps.search.Query;
import com.example.apps.search.entities.IssueResponse;
import com.example.apps.search.helpers.QueryBuilder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@ConfigurationProperties(prefix = "api")
public class SearchService {

    @Autowired
    RestOperations rest;

    @Value("${api.github.search.issues}")
    String api;

    public IssueResponse getIssues(Query q, Order o) {

        QueryBuilder queries = QueryBuilder.fromQuery(q);

        IssueResponse response = null;
        
        if (queries.isEmpty()) {
            log.debug("queries is empty, skip rest operation.");
        } else {
            URI uri = UriComponentsBuilder.fromUriString(api)
                    .queryParam("q", queries.build())
                    .queryParam("sort", o.getSort())
                    .queryParam("order", o.getOrder())
                    .build()
                    .toUri();
            log.debug("rest operation for uri [{}]", uri.toString());

            ResponseEntity<IssueResponse> entity = rest.getForEntity(uri, IssueResponse.class);

            response = entity.getBody();
        }
        
        return response;
    }
}
