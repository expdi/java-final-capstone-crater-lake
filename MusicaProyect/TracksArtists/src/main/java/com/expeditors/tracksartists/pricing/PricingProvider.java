package com.expeditors.tracksartists.pricing;

import com.expeditors.tracksartists.services.implemetations.models.Track;

public interface PricingProvider {
    void addPrice(Track track);
}
