package com.expeditors.pricing.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping("/api/pricing/")
public class PricingController {

    @GetMapping()
    public ResponseEntity<?> getPrice(){
        return ResponseEntity.ok(ThreadLocalRandom.current().nextDouble(1, 10000));
    }
}
