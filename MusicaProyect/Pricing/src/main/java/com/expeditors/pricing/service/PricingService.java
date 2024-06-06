package com.expeditors.pricing.service;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

@Setter
@Getter
@Service
public class PricingService  {

    @Value("${expeditors.pricing.lowerLimit}")
    private double lowerLimit;

    @Value("${expeditors.pricing.upperLimit}")
    private double upperLimit;

}
