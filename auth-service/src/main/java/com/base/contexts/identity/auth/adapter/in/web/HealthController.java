package com.base.contexts.identity.auth.adapter.in.web;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController 
public class HealthController {
    @GetMapping("/health") 
    public Map<String,String> health() { 
        return Map.of("status","UP"); 
    }
}
