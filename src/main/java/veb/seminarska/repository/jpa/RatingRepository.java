package veb.seminarska.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import veb.seminarska.model.Rating;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
}
