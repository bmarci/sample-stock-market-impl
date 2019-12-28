package com.opencast.martonblum.integration.controller;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContext.class})
@WebAppConfiguration
class StockControllerTest {

    private MockMvc mockMvc;

    @Test
    void addStock() {
    }

    @Test
    void addStock1() {
    }

    @Test
    void calculateDividend() {
    }

    @Test
    void calculateVolumeWeighted() {
    }

    @Test
    void calculateGeometricMean() {
    }
}