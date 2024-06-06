package com.expeditors.pricing;

import com.expeditors.pricing.service.PricingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class PricingApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PricingService pricingService;

    @Test
    @WithMockUser(roles = "USER")
    public void testGetLimits() throws Exception {

        when(pricingService.getLowerLimit()).thenReturn(0.0);
        when(pricingService.getUpperLimit()).thenReturn(100.0);

        var actions = mockMvc.perform(get("/lowerLimit"))
                .andExpect(status().isOk()).andReturn();

        var ll = actions.getResponse().getContentAsString();

        actions = mockMvc.perform(get("/upperLimit"))
                .andExpect(status().isOk()).andReturn();

        var ul = actions.getResponse().getContentAsString();

        assertEquals("0.0:100.0",ll + ": " + ul);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testSetLimits() throws Exception {
        var actionsLower = mockMvc.perform(put("/lowerLimit/{ll}", 5.0))
                .andExpect(status().isForbidden()).andReturn();
        var actionsUpper = mockMvc.perform(put("/upperLimit/{ul}", 80.0))
                .andExpect(status().isForbidden()).andReturn();
        var lower = actionsLower.getResponse().getContentAsString();
        var upper = actionsUpper.getResponse().getContentAsString();
        assertEquals("5.0", lower);
        assertEquals("80.0", upper);
    }

}
