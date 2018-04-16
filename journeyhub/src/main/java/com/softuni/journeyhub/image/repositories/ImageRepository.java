package com.softuni.journeyhub.image.repositories;

import com.softuni.journeyhub.image.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
}
