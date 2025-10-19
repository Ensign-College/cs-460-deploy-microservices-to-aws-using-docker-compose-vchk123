package com.example.explorecalijpa.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class FeatureFlagService {

    @Value("${features.tour-ratings:true}")
    private boolean tourRatingsEnabled;

    public boolean isTourRatingsEnabled() {
        return tourRatingsEnabled;
    }
}