package com.expeditors.pricing.Controllers;

import com.expeditors.pricing.service.PricingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PricingController.class)
class PricingControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PricingService pricingService;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                //.apply(springSecurity())
                .build();
    }

    @Test
    void getPrice() throws Exception {
        when(pricingService.getLowerLimit()).thenReturn(10.0);
        when(pricingService.getUpperLimit()).thenReturn(20.0);

        mockMvc.perform(
                get("/api/pricing")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(print());

        verify(pricingService).getLowerLimit();
        verify(pricingService).getUpperLimit();
    }

    @WithMockUser(value = "andre", roles = {"ADMIN", "USER"})
    @Test
    void setLowerLimit() throws Exception {
        double ll = 10.0D;
        doNothing().when(pricingService).setLowerLimit(ll);

        mockMvc.perform(
                        put("/api/pricing/lowerLimit/{ll}", ll)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNoContent())
                .andDo(print());

        verify(pricingService).setLowerLimit(ll);
    }

    @Test
    void setUpperLimit() throws Exception {
        double ll = 20.0D;
        doNothing().when(pricingService).setUpperLimit(ll);

        mockMvc.perform(
                        put("/api/pricing/upperLimit/{ll}", ll)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNoContent())
                .andDo(print());

        verify(pricingService).setUpperLimit(ll);
    }

    @Test
    void getLowerLimit() throws Exception {
        double ll = 10.0F;

        when(pricingService.getLowerLimit()).thenReturn(ll);

        mockMvc.perform(
                get("/api/pricing/lowerLimit")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(ll))
                .andDo(print());

        verify(pricingService).getLowerLimit();
    }

    @Test
    void getUpperLimit() throws Exception {
        double ul = 20.0F;

        when(pricingService.getUpperLimit()).thenReturn(ul);

        mockMvc.perform(
                        get("/api/pricing/upperLimit")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(ul))
                .andDo(print());

        verify(pricingService).getUpperLimit();
    }

    @Test
    void getBothLimits() throws Exception {
        double ll = 10.0;
        double ul = 20.0;

        when(pricingService.getLowerLimit()).thenReturn(ll);
        when(pricingService.getUpperLimit()).thenReturn(ul);

        mockMvc.perform(
                        get("/api/pricing/bothLimits")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(ll + ":" + ul))
                .andDo(print());

        verify(pricingService).getLowerLimit();
        verify(pricingService).getUpperLimit();
    }
}