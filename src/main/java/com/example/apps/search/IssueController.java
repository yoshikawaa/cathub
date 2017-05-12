package com.example.apps.search;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestOperations;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.apps.search.entities.IssueResponse;
import com.example.apps.search.helpers.QueryBuilder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("issues")
@ConfigurationProperties(prefix = "api")
public class IssueController {

    @Value("${api.github.search.issues}")
    String api;

    @Autowired
    RestOperations rest;

    @ModelAttribute
    public Query query() {
        return new Query();
    }

    @ModelAttribute
    public Order order() {
        return new Order();
    }

    @GetMapping
    public String index() {
        return "views/issues";
    }

    @PostMapping
    public String search(Model model,
            @Validated Query q,
            @Validated Order o,
            BindingResult result) {

        if (result.hasErrors()) { return index(); }

        QueryBuilder queries = QueryBuilder.fromQuery(q);

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
            model.addAttribute("issues", entity.getBody().getItems());
        }

        return index();
    }
}
