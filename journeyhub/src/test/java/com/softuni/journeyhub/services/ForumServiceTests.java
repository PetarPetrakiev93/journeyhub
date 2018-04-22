package com.softuni.journeyhub.services;

import com.softuni.journeyhub.forum.entities.Topic;
import com.softuni.journeyhub.forum.model.PostAdd;
import com.softuni.journeyhub.forum.model.TopicAdd;
import com.softuni.journeyhub.forum.repositories.PostRepository;
import com.softuni.journeyhub.forum.repositories.TopicRepository;
import com.softuni.journeyhub.forum.services.TopicServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ForumServiceTests {
    @Mock
    private TopicRepository topicRepository;
    @Mock
    private PostRepository postRepository;
    @InjectMocks
    private TopicServiceImpl topicService;

    private TopicAdd topicAdd;

    private PostAdd postAdd;

    @Before
    public void setUp() {
        this.topicAdd = new TopicAdd();
        topicAdd.setName("Putuvane na Zapad");

        this.postAdd = new PostAdd();
        postAdd.setText("testing");

        when(this.topicRepository.save(any())).thenAnswer(a->a.getArgument(0));
        when(this.postRepository.save(any())).thenAnswer(a->a.getArgument(0));
    }

    @Test
    public void createTopic_WithValidTopic_ShouldNotReturnNull(){
        Topic topic = topicService.addTopic(this.topicAdd, "Test");

        Assert.assertNotEquals("Topic is null after creation", null, topic);
    }

    @Test
    public void createTopic_WithValidArguments_ShouldReturnValidArguments(){
        Topic topic = topicService.addTopic(topicAdd, "Test");


        Assert.assertEquals("Topic entity name not mapped", this.topicAdd.getName(), topic.getName());
        Assert.assertEquals("Topic entity creator not mapped", "Test", topic.getCreator());
    }
}
