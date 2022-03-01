package veb.seminarska.service;


import veb.seminarska.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    Category create(String name, String description);

    Category update(String name, String description);

    void delete(String name);

    List<Category> listCategories();

    List<Category> searchCategories(String searchText);

    void deleteById(Long id);

    Optional<Category> save(String name, String description);

    Optional<Category> findById(Long id);
}
