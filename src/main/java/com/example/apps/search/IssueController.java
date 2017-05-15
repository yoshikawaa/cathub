package com.example.apps.search;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.apps.search.entities.IssueResponse;
import com.example.apps.search.services.SearchService;
import com.example.apps.settings.Settings;

@Controller
@RequestMapping("issues")
@ConfigurationProperties(prefix = "api")
public class IssueController {

    @Autowired
    private Settings settings;

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

    @GetMapping
    public String index(Query q) {
        if (StringUtils.isEmpty(q.getOrg())) { q.setOrg(settings.getOrg()); }
        if (StringUtils.isEmpty(q.getRepo())) { q.setRepo(settings.getRepo()); }
        return "views/issues";
    }

    @PostMapping
    public String search(Model model,
            @Validated Query q,
            @Validated Order o,
            BindingResult result) {

        if (result.hasErrors()) { return index(q); }

        IssueResponse response = service.getIssues(q, o);
        if (response != null) {
            model.addAttribute("issues", response.getItems());
        }

        return index(q);
    }
}
