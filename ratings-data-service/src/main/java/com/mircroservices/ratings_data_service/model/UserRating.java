package com.mircroservices.ratings_data_service.model;

import java.util.List;

public class UserRating {

    private List<Rating> userRatings;

    public UserRating() {
    }

    public UserRating(List<Rating> userRatings) {
        this.userRatings = userRatings;
    }

    public List<Rating> getUserRatings() {
        return userRatings;
    }

    public void setUserRatings(List<Rating> userRatings) {
        this.userRatings = userRatings;
    }
}
