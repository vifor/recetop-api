package com.recetop.api.controller;

import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.cache.CacheManager;
import java.util.Objects;

@RestController
@RequestMapping("/testing")
@Profile("test")
public class TestingController {

    private final CacheManager cacheManager;

    public TestingController(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @PostMapping("/reset-rate-limiter")
    public ResponseEntity<Void> resetRateLimiter() {
        // The cache name "buckets" comes from your ehcache.xml
        Objects.requireNonNull(cacheManager.getCache("buckets")).clear();
        return ResponseEntity.noContent().build();
    }
}