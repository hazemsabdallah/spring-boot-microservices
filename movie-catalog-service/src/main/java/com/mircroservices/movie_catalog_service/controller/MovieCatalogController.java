package com.mircroservices.movie_catalog_service.controller;

import com.mircroservices.movie_catalog_service.model.CatalogItem;
import com.mircroservices.movie_catalog_service.model.Movie;
import com.mircroservices.movie_catalog_service.model.Rating;
import com.mircroservices.movie_catalog_service.model.UserRating;
import com.mircroservices.movie_catalog_service.service.MovieInfoService;
import com.mircroservices.movie_catalog_service.service.RatingsDataService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogController {

    private final RestTemplate restTemplate;

    private final WebClient.Builder webClientBuilder;

    private final MovieInfoService movieInfoService;

    private final RatingsDataService ratingsDataService;

    @Autowired
    public MovieCatalogController(RestTemplate restTemplate, WebClient.Builder webClientBuilder, MovieInfoService movieInfoService, RatingsDataService ratingsDataService) {
        this.restTemplate = restTemplate;
        this.webClientBuilder = webClientBuilder;
        this.movieInfoService = movieInfoService;
        this.ratingsDataService = ratingsDataService;
    }

    @GetMapping("/{userId}")
//    @CircuitBreaker(name = "catalogCircuitBreaker", fallbackMethod = "getFallbackCatalogItems")
    public List<CatalogItem> getCatalogItems(@PathVariable("userId") String userId) {

        UserRating userRating = ratingsDataService.getUserRating(userId);
        return userRating.getUserRatings().stream()
                .map(movieInfoService::getCatalogItem)
                .toList();
    }

    private CatalogItem getCatalogItemWebClient(Rating rating) {
        Movie movie = webClientBuilder.build()
                .get()
                .uri("http://localhost:8082/movies/" + rating.getMovieId())
                .retrieve()
                .bodyToMono(Movie.class)
                .block();
        return new CatalogItem(movie.getName(), movie.getDescription(), rating.getRating());
    }

//    public List<CatalogItem> getFallbackCatalogItems(String userId, Throwable throwable) {
//        return List.of(new CatalogItem("none", "none", 0));
//    }
}
