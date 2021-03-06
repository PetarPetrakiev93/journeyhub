package com.softuni.journeyhub.forum.model;

import com.softuni.journeyhub.forum.entities.Post;

import java.util.List;

public class ModelWithPosts {
    private Long id;

    private String name;

    private List<Post> posts;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
}
