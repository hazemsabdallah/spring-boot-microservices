package com.mircroservices.movie_catalog_service.service;

import com.mircroservices.movie_catalog_service.model.CatalogItem;
import com.mircroservices.movie_catalog_service.model.Movie;
import com.mircroservices.movie_catalog_service.model.Rating;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MovieInfoService {

    private final RestTemplate restTemplate;

    @Autowired
    public MovieInfoService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @CircuitBreaker(name = "catalogItemCircuitBreaker", fallbackMethod = "getFallbackCatalogItem")
    public CatalogItem getCatalogItem(Rating rating) {
        Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);
        return new CatalogItem(movie.getName(), movie.getDescription(), rating.getRating());
    }

    private CatalogItem getFallbackCatalogItem(Rating rating, Throwable throwable) {
        return new  CatalogItem("not found", "not found", rating.getRating());
    }
}
