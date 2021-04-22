package org.mql.spring.gatewayservice.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class CircuitBreakerRestController {
    private Map<String,String> countries = new HashMap<>();

    @GetMapping("/defaultCountries")
    public Map<String,String> countries(){
        countries.put("message","default countries");
        countries.put("countries","Maroc, Canada, USA, Tunisie , UK");
        return countries;
    }

}
