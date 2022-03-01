package veb.seminarska.service.impl;

import org.springframework.stereotype.Service;
import veb.seminarska.model.Category;
import veb.seminarska.repository.jpa.CategoryRepository;
import veb.seminarska.service.CategoryService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category create(String name, String description) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException();
        }
        Category c = new Category(name, description);
        categoryRepository.save(c);
        return c;
    }

    @Override
    public Category update(String name, String description) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException();
        }
        Category c = new Category(name, description);
        categoryRepository.save(c);
        return c;
    }

    @Override
    public void delete(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException();
        }
        categoryRepository.deleteByName(name);
    }

    @Override
    public List<Category> listCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public List<Category> searchCategories(String searchText) {
        return categoryRepository.findAllByNameLike(searchText);
    }

    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Optional<Category> save(String name, String description) {


        this.categoryRepository.deleteByName(name);
        return Optional.of(this.categoryRepository.save(new Category(name, description)));
    }

    @Override
    public Optional<Category> findById(Long id) {
        return this.categoryRepository.findById(id);
    }
}

