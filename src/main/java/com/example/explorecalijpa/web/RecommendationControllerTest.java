package com.example.explorecalijpa.web;

import com.example.explorecalijpa.business.RecommendationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RecommendationController.class)
class RecommendationControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private RecommendationService service;

  @Test
  @DisplayName("GET /recommendations/top/{limit} - valid limit returns JSON")
  void testGetTopRecommendationsValid() throws Exception {
    // Mock service response
    when(service.getTopRecommendations(3)).thenReturn(
        List.of(
            Map.of("tourId", 1, "title", "A", "avgRating", 5, "reviewCount", 10),
            Map.of("tourId", 2, "title", "B", "avgRating", 4, "reviewCount", 8)));

    mockMvc.perform(get("/recommendations/top/3")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(2))
        .andExpect(jsonPath("$[0].tourId").value(1))
        .andExpect(jsonPath("$[1].tourId").value(2));
  }

  @Test
  @DisplayName("GET /recommendations/top/{limit} - invalid limit returns 400")
  void testGetTopRecommendationsInvalidLimit() throws Exception {
    mockMvc.perform(get("/recommendations/top/0")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
    mockMvc.perform(get("/recommendations/top/101")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("GET /recommendations/top/{limit} - empty results returns []")
  void testGetTopRecommendationsEmpty() throws Exception {
    when(service.getTopRecommendations(anyInt())).thenReturn(List.of());

    mockMvc.perform(get("/recommendations/top/5")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().json("[]"));
  }

  @Test
  @DisplayName("GET /recommendations/customer/{customerId}?limit=5 - valid customer returns JSON")
  void testCustomerRecommendationsDefaultLimit() throws Exception {
    when(service.getCustomerRecommendations(2, 5)).thenReturn(
        List.of(
            Map.of("tourId", 3, "title", "C", "avgRating", 5, "reviewCount", 4)));

    mockMvc.perform(get("/recommendations/customer/2"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(1))
        .andExpect(jsonPath("$[0].tourId").value(3));
  }

  @Test
  @DisplayName("GET /recommendations/customer/{customerId}?limit=invalid - returns 400")
  void testCustomerRecommendationsInvalidLimit() throws Exception {
    mockMvc.perform(get("/recommendations/customer/2?limit=0"))
        .andExpect(status().isBadRequest());

    mockMvc.perform(get("/recommendations/customer/0?limit=5"))
        .andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("GET /recommendations/customer/{customerId} - empty results returns []")
  void testCustomerRecommendationsEmpty() throws Exception {
    when(service.getCustomerRecommendations(anyInt(), anyInt())).thenReturn(List.of());

    mockMvc.perform(get("/recommendations/customer/2?limit=5"))
        .andExpect(status().isOk())
        .andExpect(content().json("[]"));
  }
}
