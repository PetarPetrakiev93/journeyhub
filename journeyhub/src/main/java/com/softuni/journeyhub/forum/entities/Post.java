package com.softuni.journeyhub.forum.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "topics")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    private Long author;

    private Date date;

    @ManyToOne
    @JoinColumn(name = "post_id", referencedColumnName = "id")
    private Topic topic;

    public Post() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getAuthor() {
        return author;
    }

    public void setAuthor(Long author) {
        this.author = author;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
