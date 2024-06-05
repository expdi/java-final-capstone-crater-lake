package com.expeditors.pricing.dataAccessObjects.Classes;

import com.expeditors.pricing.Models.Pricing;
import com.expeditors.pricing.dataAccessObjects.IPricingDao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class PricingDao implements IPricingDao {
    Map<Integer, Pricing> prices = new HashMap<>();

    @Override
    public double getPriceByTrack(int id) {
        return this.prices.get(id).getPrice();
    }
}
