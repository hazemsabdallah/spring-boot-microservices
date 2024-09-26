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

    @GetMapping("/{userId}")
    public Rating getRatings(@PathVariable("userId") String userId) {
        return new Rating(userId, 6);
    }
}
