package com.example.app.search.common;

import com.example.core.validation.constraints.In;

import lombok.Getter;
import lombok.Setter;

/**
 * @see https://developer.github.com/v3/search/#parameters-3
 */
@Getter
@Setter
public class Order {
    @In({ "comments", "created", "updated" })
    private String sort;
    @In({ "asc", "desc" })
    private String order;
}
