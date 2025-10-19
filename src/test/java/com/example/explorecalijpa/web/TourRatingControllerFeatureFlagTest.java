package com.example.explorecalijpa.web;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.explorecalijpa.business.TourRatingService;
import com.example.explorecalijpa.config.FeatureFlagService;

@WebMvcTest
public class TourRatingControllerFeatureFlagTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TourRatingService tourRatingService;

    @MockBean
    private FeatureFlagService featureFlagService;

    @Test
    public void testGetAllRatingsFeatureFlagOff() throws Exception {
        when(featureFlagService.isTourRatingsEnabled()).thenReturn(false);

        mockMvc.perform(get("/tours/1/ratings"))
                .andExpect(status().isNotFound());
    }
}