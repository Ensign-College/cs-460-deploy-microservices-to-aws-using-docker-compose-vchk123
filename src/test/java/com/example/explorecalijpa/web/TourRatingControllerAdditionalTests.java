package com.example.explorecalijpa.web;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.explorecalijpa.business.TourRatingService;
import com.example.explorecalijpa.config.FeatureFlagService;

@WebMvcTest
public class TourRatingControllerAdditionalTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TourRatingService tourRatingService;

    @MockBean
    private FeatureFlagService featureFlagService;

    @Test
    public void testCreateTourRatingFeatureFlagOff() throws Exception {
        when(featureFlagService.isTourRatingsEnabled()).thenReturn(false);

        mockMvc.perform(post("/tours/1/ratings")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"customerId\": 1, \"score\": 5, \"comment\": \"Great!\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateWithPutFeatureFlagOff() throws Exception {
        when(featureFlagService.isTourRatingsEnabled()).thenReturn(false);

        mockMvc.perform(put("/tours/1/ratings")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"customerId\": 1, \"score\": 4, \"comment\": \"Good\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteFeatureFlagOff() throws Exception {
        when(featureFlagService.isTourRatingsEnabled()).thenReturn(false);

        mockMvc.perform(delete("/tours/1/ratings/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateManyTourRatingsFeatureFlagOff() throws Exception {
        when(featureFlagService.isTourRatingsEnabled()).thenReturn(false);

        mockMvc.perform(post("/tours/1/ratings/batch")
                .contentType(MediaType.APPLICATION_JSON)
                .content("[1, 2, 3]"))
                .andExpect(status().isNotFound());
    }
}