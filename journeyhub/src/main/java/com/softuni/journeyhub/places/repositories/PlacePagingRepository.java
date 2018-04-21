package com.softuni.journeyhub.places.repositories;

import com.softuni.journeyhub.places.entities.Place;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlacePagingRepository extends PagingAndSortingRepository<Place, Long> {
}
