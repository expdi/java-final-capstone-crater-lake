package com.expeditors.pricing.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@TestPropertySource(properties = {
        "expeditors.pricing.lowerLimit=100.0",
        "expeditors.pricing.upperLimit=500.0"
})
class PricingServiceTest {

    @InjectMocks
    private PricingService pricingService;

    @BeforeEach
    public void setUp() {
        pricingService.setLowerLimit(10.0);
        pricingService.setUpperLimit(20.0);
    }

    @Test
    void getLowerLimit() {
        assertEquals(10.0, pricingService.getLowerLimit());
    }

    @Test
    void getUpperLimit() {
        assertEquals(20.0, pricingService.getUpperLimit());
    }

    @Test
    void setLowerLimit() {
        pricingService.setLowerLimit(15.0);
        assertEquals(15.0, pricingService.getLowerLimit());
    }

    @Test
    void setUpperLimit() {
        pricingService.setUpperLimit(25.0);
        assertEquals(25.0, pricingService.getUpperLimit());
    }
}