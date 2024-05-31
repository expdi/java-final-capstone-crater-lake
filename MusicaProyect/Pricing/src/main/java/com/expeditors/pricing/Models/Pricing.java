package com.expeditors.pricing.Models;

import lombok.Data;

import java.util.concurrent.ThreadLocalRandom;

@Data
public class Pricing {
    private int trackId;
    private double price;

    public double getPrice(){
        return ThreadLocalRandom.current().nextDouble(1, 10000);
    }
}
