package com.softuni.journeyhub.scheduled;

import com.softuni.journeyhub.tours.services.TourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SuggestedTours {

    private TourService tourService;

    @Autowired
    public SuggestedTours(TourService tourService) {
        this.tourService = tourService;
    }

    @Scheduled(fixedRate = 300000)
    public void updateTours(){
        this.tourService.updateSuggestedTours();
    }
}
