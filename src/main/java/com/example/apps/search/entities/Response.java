package com.example.apps.search.entities;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class Response<T> {
    private Integer totalCount;
    private Boolean incompleteResults;
    private List<T> items;
}
