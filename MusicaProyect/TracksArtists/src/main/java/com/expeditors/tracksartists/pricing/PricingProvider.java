package com.expeditors.tracksartists.pricing;

import com.expeditors.tracksartists.models.Track;

public interface PricingProvider {
    void addPrice(Track track);
}
