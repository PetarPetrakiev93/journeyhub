package com.softuni.journeyhub.forum.services;

import com.softuni.journeyhub.forum.entities.Post;
import com.softuni.journeyhub.forum.entities.Topic;
import com.softuni.journeyhub.forum.model.ModelWithPosts;
import com.softuni.journeyhub.forum.model.PostAdd;
import com.softuni.journeyhub.forum.model.TopicAdd;
import com.softuni.journeyhub.forum.model.TopicShow;
import com.softuni.journeyhub.forum.repositories.PostRepository;
import com.softuni.journeyhub.forum.repositories.TopicRepository;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TopicServiceImpl implements TopicService {

    private TopicRepository topicRepository;

    private PostRepository postRepository;

    private ModelMapper modelMapper;

    @Autowired
    public TopicServiceImpl(TopicRepository topicRepository, PostRepository postRepository, ModelMapper modelMapper) {
        this.topicRepository = topicRepository;
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Topic addTopic(TopicAdd topicAdd,String username) {
        Topic topic = new Topic();
        topic.setName(HtmlUtils.htmlEscape(topicAdd.getName()));
        topic.setCreator(username);
        return this.topicRepository.save(topic);
    }

    @Override
    public void deleteTopic(Long id) {
        this.topicRepository.delete(this.topicRepository.getOne(id));
    }

    @Override
    public List<TopicShow> getAllTopics() {
        Converter<Date, String> formatDate = ctx ->formatDateToString(ctx.getSource());
        TypeMap<Topic, TopicShow> topicTypeMap = this.modelMapper.typeMap(Topic.class, TopicShow.class);
        try {
            topicTypeMap.addMappings(mapper -> mapper.using(formatDate).map(Topic::getDate, TopicShow::setDate));
        }catch (Exception e){
        }
        List<Topic> topics = this.topicRepository.findAll();
        return topics.stream().map(topic -> topicTypeMap.map(topic)).collect(Collectors.toList());
    }

    @Override
    public ModelWithPosts getTopic(Long id) {
        Topic topic = this.topicRepository.getOne(id);
        ModelWithPosts modelWithPosts = new ModelWithPosts();
        this.modelMapper.map(topic, modelWithPosts);
        modelWithPosts.setPosts(topic.getPosts());
        return modelWithPosts;
    }

    @Override
    public void addPost(PostAdd postAdd, String username, Long id) {
        Post post = new Post();
        post.setText(HtmlUtils.htmlEscape(postAdd.getText()));
        post.setAuthor(username);
        Topic topic = this.topicRepository.getOne(id);
        post.setTopic(topic);
        topic.getPosts().add(this.postRepository.save(post));
        this.topicRepository.save(topic);
    }

    @Override
    public void updateTopicViews(String id) {
        long temp = Long.parseLong(id);
        Topic topic = this.topicRepository.getOne(temp);
        topic.setViews(topic.getViews()+1);
        this.topicRepository.save(topic);
    }

    @Override
    public List<Topic> getOrderedTopics() {
        return this.topicRepository.findAllByOrderByViewsDesc();
    }

    public static String formatDateToString(Object v) {
        String dateFormat = "yyyy-MM-dd";
        if(v!=null)
            return new SimpleDateFormat(dateFormat).format(v).toString();
        else return "";
    }
}
