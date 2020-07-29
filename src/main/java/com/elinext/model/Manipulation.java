package com.elinext.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Manipulation {
    private Long id;
    private String name;
    private String description;

    public Manipulation (String name, String description) {
        this.name = name;
        this.description = description;
    }
}
