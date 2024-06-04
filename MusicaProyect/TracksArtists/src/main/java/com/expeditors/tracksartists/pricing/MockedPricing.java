package com.expeditors.tracksartists.pricing;

import com.expeditors.tracksartists.models.Track;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public abstract class MockedPricing implements PricingProvider{


    @Autowired
    private Pricing restClientHolder;

    @Override
    public void addPrice(Track track) {
        Double price = restClientHolder.getPrice();

        track.setPrice(price);
    }
}
