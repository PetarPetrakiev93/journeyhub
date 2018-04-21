package com.softuni.journeyhub.places.repositories;

import com.softuni.journeyhub.places.entities.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {
    List<Place> findAllByOrderByRatingDesc();
}
