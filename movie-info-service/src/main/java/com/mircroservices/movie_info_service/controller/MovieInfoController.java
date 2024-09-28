package com.mircroservices.movie_info_service.controller;

import com.mircroservices.movie_info_service.model.Movie;
import com.mircroservices.movie_info_service.model.MovieSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/movies")
public class MovieInfoController {

    @Value("${api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    @Autowired
    public MovieInfoController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/{movieId}")
    public Movie getMovie(@PathVariable("movieId") String movieId) {
        MovieSummary movieSummary = restTemplate.getForObject(
                "https://api.themoviedb.org/3/movie/" + movieId + "?api_key=" + apiKey,
                MovieSummary.class
        );
        return new Movie(movieId, movieSummary.getTitle(), movieSummary.getOverview());
    }
}
