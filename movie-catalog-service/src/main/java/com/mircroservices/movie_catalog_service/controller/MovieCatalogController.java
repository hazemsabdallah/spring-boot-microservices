package com.mircroservices.movie_catalog_service.controller;

import com.mircroservices.movie_catalog_service.model.CatalogItem;
import com.mircroservices.movie_catalog_service.model.Movie;
import com.mircroservices.movie_catalog_service.model.Rating;
import com.mircroservices.movie_catalog_service.model.UserRating;
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

    @Autowired
    public MovieCatalogController(RestTemplate restTemplate, WebClient.Builder webClientBuilder) {
        this.restTemplate = restTemplate;
        this.webClientBuilder = webClientBuilder;
    }

    @GetMapping("/{userId}")
    public List<CatalogItem> getCatalogItems(@PathVariable("userId") String userId) {

        List<Rating> ratings = restTemplate.getForObject("http://localhost:8083/ratings/users/" + userId, UserRating.class).getUserRatings();

        return ratings.stream()
                .map(rating -> {
                    Movie movie = restTemplate.getForObject("http://localhost:8082/movies/" + rating.getMovieId(), Movie.class);

//                    // trying out webclient reactive
//                    Movie movie = webClientBuilder.build()
//                            .get()
//                            .uri("http://localhost:8082/movies/" + rating.getMovieId())
//                            .retrieve()
//                            .bodyToMono(Movie.class)
//                            .block();

                    return new CatalogItem(movie.getName(), "test desc", rating.getRating());
                })
                .toList();
    }
}
