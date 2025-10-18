package com.example.explorecalijpa.business;

import com.example.explorecalijpa.repo.RecommendationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RecommendationService {

  private final RecommendationRepository repo;

  public RecommendationService(RecommendationRepository repo) {
    this.repo = repo;
  }

  public List<Map<String, Object>> getTopRecommendations(int limit) {
    return repo.findTopTours().stream()
        .limit(limit)
        .map(row -> Map.of(
            "tourId", row[0],
            "title", row[1],
            "avgRating", row[2],
            "reviewCount", row[3]))
        .collect(Collectors.toList());
  }

  public List<Map<String, Object>> getCustomerRecommendations(int customerId, int limit) {
    return repo.findRecommendedToursForCustomer(customerId).stream()
        .limit(limit)
        .map(row -> Map.of(
            "tourId", row[0],
            "title", row[1],
            "avgRating", row[2],
            "reviewCount", row[3]))
        .collect(Collectors.toList());
  }
}
