package com.example.apps.settings;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Component
@SessionScope
public class Settings {
    @NotBlank
    private String user;
    private String pass;
    private String org;
    private String repo;
}
