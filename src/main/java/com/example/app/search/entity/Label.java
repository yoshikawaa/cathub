package com.example.app.search.entity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Label {
    private String name;
    private String color;
    private String url;
    
    public String getColorCode() {
        return "#" + color;
    }
    
    public String getHtmlUrl() {
        return url.replace("api.", "").replace("repos/", "");
    }
}
