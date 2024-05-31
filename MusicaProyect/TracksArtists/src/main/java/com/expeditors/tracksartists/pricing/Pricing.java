package com.expeditors.tracksartists.pricing;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class Pricing {
    private RestClient restClient;

    private String pricingUrl;

    public Pricing() {
        var baseUrl = "http://localhost:10001";
        var rootUrl = "/api/pricing";
        pricingUrl = rootUrl + "/{id}";

        this.restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader("Accept", "application/json")
                .defaultHeader("Content-Type", "application/json")
                .build();
    }

    public Double getPrice() {
        ResponseEntity<Double> response = restClient.get()
                .retrieve()
                .toEntity(Double.class);
        if(response.getBody() != null){

            return response.getBody();
        }

        return 0.0;
    }
}
