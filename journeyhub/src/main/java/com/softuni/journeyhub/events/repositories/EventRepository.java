package com.softuni.journeyhub.events.repositories;

import com.softuni.journeyhub.events.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
}
