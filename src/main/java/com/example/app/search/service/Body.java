package com.example.app.search.service;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Body<T> {
    @JsonProperty("total_count")
    private Integer totalCount;
    @JsonProperty("incomplete_results")
    private Boolean incompleteResults;
    private List<T> items;
}
