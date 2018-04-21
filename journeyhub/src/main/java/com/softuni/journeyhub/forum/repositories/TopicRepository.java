package com.softuni.journeyhub.forum.repositories;

import com.softuni.journeyhub.forum.entities.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {

    List<Topic> findAllByOrderByViewsDesc();
}
