package com.example.apps.settings;

import javax.servlet.http.HttpServletResponse;
import javax.validation.groups.Default;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.apps.settings.helpers.CookieHelper;
import com.example.core.validation.groups.Clear;
import com.example.core.validation.groups.Save;

@Controller
@RequestMapping("settings")
public class SettingsController {

    @ModelAttribute
    public Settings settings() {
        return new Settings();
    }

    public String view() {
        return "views/settings";
    }

    @GetMapping
    public String index(Model model,
            @CookieValue(name = "settings.user", required = false) String user,
            @CookieValue(name = "settings.pass", required = false) String pass,
            @CookieValue(name = "settings.org", required = false) String org) {
        model.addAttribute(new Settings(user, pass, org));
        return view();
    }

    @PostMapping(params = "save")
    public String save(Model model,
            @Validated({ Default.class, Save.class }) Settings settings,
            BindingResult result,
            HttpServletResponse response) {

        if (result.hasErrors()) {
            return view();
        }

        CookieHelper.addCookies(response, "settings", settings);
        model.addAttribute("status", "saved");
        return view();
    }

    @PostMapping(params = "clear")
    public String clear(Model model,
            @Validated({ Default.class, Clear.class }) Settings settings,
            BindingResult result,
            HttpServletResponse response) {

        if (result.hasErrors()) {
            return view();
        }

        CookieHelper.removeCookies(response, "settings", settings);
        model.addAttribute("status", "cleared");
        model.addAttribute(new Settings());
        return view();
    }
}
