package com.example.apps.settings;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("settings")
public class SettingsController {
    
    @Autowired
    private Settings settings;

    @ModelAttribute
    public Settings settings() {
        Settings settings = new Settings();
        BeanUtils.copyProperties(this.settings, settings);
        return settings;
    }

    @GetMapping
    public String index() {
        return "views/settings";
    }
    
    @PostMapping
    public String save(@Validated Settings settings, BindingResult result) {
        
        if (result.hasErrors()) { return index(); }
        
        BeanUtils.copyProperties(settings, this.settings);
        return "redirect:/settings";
    }
}
