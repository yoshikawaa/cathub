package com.example.app.search.issues;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.app.search.common.Order;
import com.example.app.search.issues.service.SearchIssuesService;

public abstract class AbstractIssuesController {

    @Autowired
    protected SearchIssuesService service;

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
            Pageable pageable) {

        if (qResult.hasErrors() || oResult.hasErrors()) {
            return view();
        }

        model.addAttribute("page", service.getIssues(q, o, pageable));
        return view();
    }

    @PostMapping(params = "download", produces = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
    public String download(Model model,
            @Validated Query q,
            BindingResult qResult,
            @Validated Order o,
            BindingResult oResult) {

        if (qResult.hasErrors() || oResult.hasErrors()) {
            return view();
        }

        model.addAttribute("issues", service.getAllIssues(q, o));
        return "issuesExcelView";
    }

    private String view() {
        return "views/issues";
    }
}
