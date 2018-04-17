package com.softuni.journeyhub.locations.repositories;

import com.softuni.journeyhub.locations.entities.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long>{
    Location getByName(String name);
}
