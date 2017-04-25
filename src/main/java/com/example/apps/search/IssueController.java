package com.example.apps.search;

import java.net.URI;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestOperations;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.apps.search.entities.Issue;
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

    @ModelAttribute("states")
    public String[] paramsIs() {
        return new String[] { "open", "closed" };
    }

    @RequestMapping(method = RequestMethod.GET)
    public String index() {
        return "views/issues";
    }

    @RequestMapping(path = "search", method = RequestMethod.GET)
    public String search(Model model,
            @Validated Query q,
            BindingResult result,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String order) {
        if (result.hasErrors()) {
            for (FieldError error : result.getFieldErrors()) {
                log.warn("error {}:{}", error.getField(), error.getCode());
            }
            return index();
        }

        QueryBuilder queries = new QueryBuilder(q);

        if (queries.isEmpty()) {
            log.debug("queries is empty, skip rest operation.");
        } else {
            URI uri = UriComponentsBuilder.fromUriString(api)
                    .queryParam("q", queries.build())
                    .queryParam("sort", sort)
                    .queryParam("order", order)
                    .build()
                    .toUri();
            log.debug("rest operation for uri [{}]", uri.toString());

            ResponseEntity<IssueResponse> entity = rest.getForEntity(uri, IssueResponse.class);
            List<Issue> issues = entity.getBody().getItems();
            issues.sort(Comparator.comparing(Issue::getRepository).reversed());

            model.addAttribute("issues", issues);
        }

        return "views/issues";
    }
}
