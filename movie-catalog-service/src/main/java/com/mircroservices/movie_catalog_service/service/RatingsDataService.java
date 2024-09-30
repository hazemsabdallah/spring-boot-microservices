package com.mircroservices.movie_catalog_service.service;

import com.mircroservices.movie_catalog_service.model.Rating;
import com.mircroservices.movie_catalog_service.model.UserRating;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class RatingsDataService {

    private final RestTemplate restTemplate;

    @Autowired
    public RatingsDataService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @CircuitBreaker(name = "userRatingCircuitBreaker", fallbackMethod = "getFallbackUserRating")
    public UserRating getUserRating(String userId) {
        return restTemplate.getForObject("http://ratings-data-service/ratings/users/" + userId, UserRating.class);
    }

    private UserRating getFallbackUserRating(String userId, Throwable throwable) {
        return new UserRating(List.of(new Rating("0", 0)));
    }
}
