package com.example.app.search.issues.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Milestone {
    private String title;
    @JsonProperty("html_url")
    private String htmlUrl;
}
