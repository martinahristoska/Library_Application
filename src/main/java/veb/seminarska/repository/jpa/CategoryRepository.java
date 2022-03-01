package veb.seminarska.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import veb.seminarska.model.Category;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAllByNameLike(String text);

    List<Category> findAllByName(String name);

    void deleteByName(String name);

    @Override
    <S extends Category> S save(S category);

    @Override
    Optional<Category> findById(Long aLong);
}
