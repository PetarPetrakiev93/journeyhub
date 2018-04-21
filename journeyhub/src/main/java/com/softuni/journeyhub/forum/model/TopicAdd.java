package com.softuni.journeyhub.forum.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class TopicAdd {
    @NotNull
    @Size(min = 10, message = "Minimum 10 symbols for title required.")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
