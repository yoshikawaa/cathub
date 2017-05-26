package com.example.apps.search;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.apps.search.entities.IssueResponse;
import com.example.apps.search.services.SearchService;

@Controller
@RequestMapping("issues")
@ConfigurationProperties(prefix = "api")
public class IssueController {

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

    public String view() {
        return "views/issues";
    }

    @GetMapping
    public String index(Query q, @CookieValue(name = "settings.org", required = false) String org) {
        q.setOrg(org);
        return view();
    }

    @PostMapping
    public String search(Model model, @Validated Query q, BindingResult qResult, @Validated Order o, BindingResult oResult) {

        if (qResult.hasErrors() || oResult.hasErrors()) {
            return view();
        }

        IssueResponse response = service.getIssues(q, o);
        if (response != null) {
            model.addAttribute("issues", response.getItems());
        }

        return view();
    }
}
