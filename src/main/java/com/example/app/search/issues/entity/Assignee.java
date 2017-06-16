package com.example.app.search.issues.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Assignee {
    @JsonProperty("html_url")
    private String htmlUrl;
    private String login;
}
