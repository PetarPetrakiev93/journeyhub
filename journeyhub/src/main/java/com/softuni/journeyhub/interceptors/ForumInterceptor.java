package com.softuni.journeyhub.interceptors;

import com.softuni.journeyhub.forum.services.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Component
public class ForumInterceptor extends HandlerInterceptorAdapter {

    private TopicService topicService;

    @Autowired
    public ForumInterceptor(TopicService topicService) {
        this.topicService = topicService;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        try {
            if (((HandlerMethod) handler).getMethod().isAnnotationPresent(UpdateViewCheck.class)) {
                Map pathVariables = (Map) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
                this.topicService.updateTopicViews((String) pathVariables.get("id"));
            }
        }catch (Exception e){

        }
        super.postHandle(request, response, handler, modelAndView);
    }
}
