package veb.seminarska.service.impl;

import org.springframework.stereotype.Service;
import veb.seminarska.model.Rating;
import veb.seminarska.model.exceptions.IllegalArgumentsException;
import veb.seminarska.repository.jpa.RatingRepository;
import veb.seminarska.service.RatingService;

import java.util.List;

@Service
public class RatingServiceImplementation implements RatingService {
    private final RatingRepository ratingRepository;

    public RatingServiceImplementation(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }


    @Override
    public List<Rating> findAll() {
        return ratingRepository.findAll();
    }

    @Override
    public Rating save(Integer number) {
        if(number == null) {
            throw new IllegalArgumentsException();
        }
        return ratingRepository.save(new Rating(number));
    }
}
