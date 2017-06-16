package com.example.app.search.repositories.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonInclude(Include.NON_NULL)
@JsonPropertyOrder({ "name", "fullName", "description", "htmlUrl", "watchersCount", "stargazersCount", "forksCount",
        "openIssuesCount" })
public class Repository {
    private String name;
    @JsonProperty("full_name")
    private String fullName;
    private String description;
    @JsonProperty("html_url")
    private String htmlUrl;
    @JsonProperty("watchers_count")
    private int watchersCount;
    @JsonProperty("stargazers_count")
    private int stargazersCount;
    @JsonProperty("forks_count")
    private int forksCount;
    @JsonProperty("open_issues_count")
    private int openIssuesCount;
}
