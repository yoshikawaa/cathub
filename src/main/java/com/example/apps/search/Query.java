package com.example.apps.search;

import com.example.apps.search.helpers.annotation.QueryParam;
import com.example.core.validation.constraints.In;

import lombok.Getter;
import lombok.Setter;

/**
 * @see https://developer.github.com/v3/search/#parameters-3
 */
@Getter
@Setter
public class Query {
    // search by user & repository
    @QueryParam(name = "user")
    private String org;
    private String repo;
    
    // search by category
    private String type;
    private String[] label;

    // search by user
    private String author;
    private String assignee;
    private String mentions;
    private String commenter;
    private String involves;
    @QueryParam(name = "reviewed-by")
    private String reviewedBy;
    @QueryParam(name = "review-requested")
    private String reviewRequested;
    private String team;

    // select by state
    @In({"open", "closed", "merged"})
    private String is;
    @In({"none", "required", "approved", "changes_requested"})
    private String review;

    // select by other conditions
    @In({"title", "body", "comments"})
    private String in;
    @QueryParam(requiredValue = false)
    private String value;

    // for example <2012-10-01
    private String created;
    private String updated;
    private String merged;
    private String closed;

    //private String no;
    //private String language;
    //private String status;
    //private String head;
    //private String base;
    //private String project;
}
