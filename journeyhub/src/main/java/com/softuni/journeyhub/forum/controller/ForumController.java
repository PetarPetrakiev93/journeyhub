package com.softuni.journeyhub.forum.controller;

import com.softuni.journeyhub.controllers.BaseController;
import com.softuni.journeyhub.forum.model.PostAdd;
import com.softuni.journeyhub.forum.model.TopicAdd;
import com.softuni.journeyhub.forum.services.TopicService;
import com.softuni.journeyhub.interceptors.UpdateViewCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class ForumController extends BaseController{
    private TopicService topicService;

    @Autowired
    public ForumController(TopicService topicService) {
        this.topicService = topicService;
    }

    @GetMapping("/forum")
    private ModelAndView getAllTopics(ModelAndView modelAndView){
        return this.view("topics", "topics", this.topicService.getAllTopics());
    }

    @GetMapping("/forum/ordered")
    private ModelAndView getOrderedTopics(ModelAndView modelAndView){
        return this.view("ordered-topics", "topics", this.topicService.getOrderedTopics());
    }

    @GetMapping("/forum/add")
    private ModelAndView getAddTopics(ModelAndView modelAndView, @ModelAttribute TopicAdd topicAdd){
        return this.view("add-topic", "addTopic", topicAdd);
    }

    @PostMapping("/forum/topic/add")
    private ModelAndView addTopic(ModelAndView modelAndView, Principal principal,
                                  @Valid @ModelAttribute TopicAdd topicAdd,
                                  BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return this.view("add-topic", "addTopic", topicAdd);
        }
        this.topicService.addTopic(topicAdd, principal.getName());
        return redirect("/forum");
    }

    @GetMapping("/forum/topic/delete/{id}")
    private ModelAndView deleteTopics(ModelAndView modelAndView, @PathVariable Long id){
        this.topicService.deleteTopic(id);
        return this.redirect("/forum");
    }

    @UpdateViewCheck
    @GetMapping("/forum/{id}")
    private ModelAndView getTopic(ModelAndView modelAndView, @PathVariable Long id, @ModelAttribute PostAdd postAdd){
        modelAndView.addObject("postAdd", postAdd);
        modelAndView.addObject("topic", this.topicService.getTopic(id));
        modelAndView.setViewName("topic-view");
        return modelAndView;
    }

    @PostMapping("/post/add/{id}")
    private ModelAndView addPost(ModelAndView modelAndView, @PathVariable Long id, @ModelAttribute PostAdd postAdd, Principal principal){
        this.topicService.addPost(postAdd, principal.getName(), id);
        return this.redirect("/forum/" + id);
    }
}
