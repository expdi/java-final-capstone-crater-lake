package com.expeditors.pricing.service;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Setter
@Getter
@Service
public class PricingService  {

    @Value("${expeditors.pricing.lowerLimit}")
    private double lowerLimit;

    @Value("${expeditors.pricing.upperLimit}")
    private double upperLimit;

}
