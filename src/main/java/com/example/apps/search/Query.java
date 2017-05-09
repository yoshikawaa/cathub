package com.example.apps.search;

import com.example.core.validator.constraints.In;

import lombok.Getter;
import lombok.Setter;

/**
 * @see https://developer.github.com/v3/search/#parameters-3
 */
@Getter
@Setter
public class Query {
    // search by user & repository
    private String user;
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
    private String team;

    // select by state
    @In({"open", "closed", "merged"})
    private String is;

    // select by other conditions
    @In({"title", "body", "comments"})
    private String in;
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
