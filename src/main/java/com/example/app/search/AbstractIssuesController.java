package com.example.app.search;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.app.search.service.SearchService;

public abstract class AbstractIssuesController {

    @Autowired
    private SearchService service;

    @ModelAttribute
    public Query query() {
        return new Query();
    }

    @ModelAttribute
    public Order order() {
        return new Order();
    }

    public String index(Query q, String org) {
        q.setOrg(org);
        return view();
    }

    @PostMapping
    public String search(Model model,
            @Validated Query q,
            BindingResult qResult,
            @Validated Order o,
            BindingResult oResult,
            @PageableDefault(size = 30) Pageable pageable) {

        if (qResult.hasErrors() || oResult.hasErrors()) {
            return view();
        }

        model.addAttribute("page", service.getIssues(q, o, pageable));
        return view();
    }

    private String view() {
        return "views/issues";
    }
}
