package com.example.app.search.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestOperations;

public abstract class AbstractSearchService {

    @Autowired
    protected RestOperations rest;

    @Value("${api.github.search.max-page-size}")
    protected int size;

    @Value("${api.github.search.max-element-size}")
    protected int limit;
}
