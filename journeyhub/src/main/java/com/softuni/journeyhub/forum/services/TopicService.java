package com.softuni.journeyhub.forum.services;

import com.softuni.journeyhub.forum.entities.Topic;
import com.softuni.journeyhub.forum.model.ModelWithPosts;
import com.softuni.journeyhub.forum.model.PostAdd;
import com.softuni.journeyhub.forum.model.TopicAdd;
import com.softuni.journeyhub.forum.model.TopicShow;

import java.util.List;

public interface TopicService {
    void addTopic(TopicAdd topicAdd, String username);
    void deleteTopic(Long id);
    List<TopicShow> getAllTopics();
    ModelWithPosts getTopic(Long id);
    void addPost(PostAdd postAdd, String username, Long id);
    void updateTopicViews(String id);
    List<Topic> getOrderedTopics();
}
