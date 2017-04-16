package com.example.github.search.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Issue {
    @JsonProperty("repository_url")
    private String repositoryUrl;
    @JsonProperty("html_url")
    private String htmlUrl;
    private Integer number;
    private String title;
    private List<Label> labels;
    private String state;
    private List<Assignee> assignees;
    private Milestone milestone;
    private Integer comments;

    public String getRepository() {
        String[] r = getRepositoryUrl().split("/");
        return r[r.length - 1];
    }
    // private User user;
}
