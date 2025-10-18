package com.example.explorecalijpa.business;

public record TourRecommendation(
    Integer tourId,
    String title,
    Double averageScore,
    Long reviewCount) {
}
