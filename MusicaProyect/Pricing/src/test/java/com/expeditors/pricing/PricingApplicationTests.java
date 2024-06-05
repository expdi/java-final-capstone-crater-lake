package com.expeditors.pricing;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class PricingApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void contextLoads() {
    }
    @Test
    public void testGetLimits() throws Exception {
        var actions = mockMvc.perform(get("/lowerLimit"))
                .andExpect(status().isOk()).andReturn();

        var ll = actions.getResponse().getContentAsString();

        actions = mockMvc.perform(get("/upperLimit"))
                .andExpect(status().isOk()).andReturn();

        var ul = actions.getResponse().getContentAsString();

        assertEquals("0.0:100.0",ll + ": " + ul);
    }

    @Test
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
