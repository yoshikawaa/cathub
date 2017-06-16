package com.example.app.search.repositories;

import com.example.core.helper.annotation.QueryParam;
import com.example.core.validation.constraints.In;

import lombok.Getter;
import lombok.Setter;

/**
 * @see https://developer.github.com/v3/search/#parameters
 */
@Getter
@Setter
public class Query {
    @QueryParam(name = "user")
    private String org;
    @In({ "name", "description", "readme" })
    private String in;
    @QueryParam(requiredValue = false)
    private String value;
}
