package veb.seminarska.service;

import veb.seminarska.model.Rating;

import java.util.List;

public interface RatingService {
    List<Rating> findAll();
    Rating save(Integer number);
}

