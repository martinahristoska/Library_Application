package veb.seminarska.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import veb.seminarska.model.Publisher;

import java.util.List;
import java.util.Optional;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Long> {
    @Override
    Optional<Publisher> findById(Long aLong);

    @Override
    void deleteById(Long aLong);

    @Override
    List<Publisher> findAll();

    @Override
    <S extends Publisher> S save(S publisher);

    void deleteByName(String name);
}

