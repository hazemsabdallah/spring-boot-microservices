package com.mircroservices.ratings_data_service.controller;

import com.mircroservices.ratings_data_service.model.Rating;
import com.mircroservices.ratings_data_service.model.UserRating;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ratings")
public class RatingsController {

    @GetMapping("/{movieId}")
    public Rating getRatings(@PathVariable("movieId") String movieId) {
        return new Rating(movieId, 6);
    }

    @GetMapping("/users/{userId}")
    public UserRating getUserRatings(@PathVariable("userId") String userId) {
        return new UserRating(List.of(
                new Rating("3", 6),
                new Rating("5", 5)
        ));
    }
}
