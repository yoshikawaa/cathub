package com.example.app.settings;

import org.hibernate.validator.constraints.NotBlank;

import com.example.core.validation.groups.Save;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Settings {
    @NotBlank(groups = Save.class)
    private String user;
    private String pass;
    private String org;
}
