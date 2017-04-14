package com.example.github.search;

import lombok.Getter;
import lombok.Setter;

/**
 * @see https://developer.github.com/v3/search/#parameters-3
 */
@Getter
@Setter
public class Query {
    
    private String type;
    private String in;
    private String author;
    private String assignee;
    private String mentions;
    private String commenter;
    private String involves;
    private String team;
    private String state;
    private String[] label;
    private String no;
    private String language;
    private String is;

    // for example <2012-10-01
    private String created;
    private String updated;
    private String merged;
    private String closed;

    //private String status;
    //private String head;
    //private String base;
    //private String user;
    //private String repo;
    //private String project;
}
