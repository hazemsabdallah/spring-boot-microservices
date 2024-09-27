package com.mircroservices.ratings_data_service.controller;

import com.mircroservices.ratings_data_service.model.Rating;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ratings")
public class MovieInfoController {

    @GetMapping("/{movieId}")
    public Rating getRatings(@PathVariable("movieId") String movieId) {
        return new Rating(movieId, 6);
    }

    @GetMapping("/users/{userId}")
    public List<Rating> getUserRatings(@PathVariable("userId") String userId) {
        return List.of(
                new Rating("movie 01", 6),
                new Rating("movie 02", 5)
        );
    }
}
