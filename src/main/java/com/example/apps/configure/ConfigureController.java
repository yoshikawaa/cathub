package com.example.apps.configure;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import lombok.extern.slf4j.Slf4j;

@SuppressWarnings("unused")
@Slf4j
// under construction
// @Controller
@RequestMapping("configure")
public class ConfigureController {

    @GetMapping
    public String index() {
        return "views/configure";
    }

    @PostMapping(params = "repos")
    public String configure(@SessionAttribute @RequestParam String org, @SessionAttribute @RequestParam String repos) {
        return "redirect:/configure";
    }
}
