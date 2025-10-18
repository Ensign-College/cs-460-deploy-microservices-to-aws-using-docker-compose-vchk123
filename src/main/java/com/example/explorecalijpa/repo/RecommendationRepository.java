package com.example.explorecalijpa.repo;

import com.example.explorecalijpa.model.TourRating;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecommendationRepository extends JpaRepository<TourRating, Integer> {

  // Compute top tours with avg rating, review count, and title
  @Query("""
      SELECT tr.tour.id AS tourId, tr.tour.title AS title,
             AVG(tr.score) AS avgRating,
             COUNT(tr) AS reviewCount
      FROM TourRating tr
      GROUP BY tr.tour.id, tr.tour.title
      ORDER BY avgRating DESC, reviewCount DESC, title ASC
      """)
  List<Object[]> findTopTours();

  // Exclude tours already rated by a specific customer
  @Query("""
      SELECT tr.tour.id AS tourId, tr.tour.title AS title,
             AVG(tr.score) AS avgRating,
             COUNT(tr) AS reviewCount
      FROM TourRating tr
      WHERE tr.tour.id NOT IN (
          SELECT tr2.tour.id FROM TourRating tr2 WHERE tr2.customerId = :customerId
      )
      GROUP BY tr.tour.id, tr.tour.title
      ORDER BY avgRating DESC, reviewCount DESC, title ASC
      """)
  List<Object[]> findRecommendedToursForCustomer(@Param("customerId") Integer customerId);
}
