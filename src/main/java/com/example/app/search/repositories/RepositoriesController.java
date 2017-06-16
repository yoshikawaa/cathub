package com.example.app.search.repositories;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.app.search.common.Order;
import com.example.app.search.repositories.entity.Repository;
import com.example.app.search.repositories.service.SearchRepositoriesService;
import com.google.common.collect.ImmutableMap;

@RestController
@RequestMapping("repositories")
public class RepositoriesController {

    @Autowired
    SearchRepositoriesService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Map<String, List<Repository>> search(@Validated Query q, @Validated Order o) {
        return ImmutableMap.of("items", service.getRepositories(q, o));
    }
}
