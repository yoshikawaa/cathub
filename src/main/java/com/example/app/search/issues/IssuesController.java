package com.example.app.search.issues;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("issues")
public class IssuesController extends AbstractIssuesController {

    @GetMapping
    public String index(Query q, @CookieValue(name = "settings.org", required = false) String org) {
        q.setType("issue");
        return super.index(q, org);
    }
}
