package com.softuni.journeyhub.tours.repositories;

import com.softuni.journeyhub.tours.entities.Tour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TourRepository extends JpaRepository<Tour, Long> {
}
