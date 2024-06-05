package com.expeditors.pricing.Controllers;

import com.expeditors.pricing.service.PricingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping("/api/pricing/")
public class PricingController {

    @Autowired
    private PricingService pricingService;

    @GetMapping()
    public ResponseEntity<?> getPrice() {
        return ResponseEntity.ok(ThreadLocalRandom.current().nextDouble(pricingService.getLowerLimit(), pricingService.getUpperLimit()));
    }

    @PutMapping("/lowerLimit/{ll}")
    public void setLowerLimit(@PathVariable("ll") double lowerLimit) {
        pricingService.setLowerLimit(lowerLimit);
    }

    @PutMapping("/upperLimit/{ul}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void setUpperLimit(@PathVariable("ul") double upperLimit) {
        pricingService.setUpperLimit(upperLimit);
    }

    @GetMapping("/lowerLimit")
    public double getLowerLimit() {
        return pricingService.getLowerLimit();
    }

    @GetMapping("/upperLimit")
    public double getUpperLimit() {
        return pricingService.getUpperLimit();
    }

    @GetMapping("/bothLimits")
    public String getBothLimits() {
        return pricingService.getLowerLimit() + ":" + pricingService.getUpperLimit();
    }

}
