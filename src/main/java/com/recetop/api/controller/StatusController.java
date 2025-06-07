package com.recetop.api.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
public class StatusController {

    @GetMapping("/")
    public Map<String, String> getStatus() {
        // Returns a clean JSON response
        return Map.of(
            "application", "Recipes API",
            "status", "running",
            "version", "1.0.0"
        );
    }
}