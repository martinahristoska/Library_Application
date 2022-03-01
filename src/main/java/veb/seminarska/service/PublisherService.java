package veb.seminarska.service;

import veb.seminarska.model.Publisher;

import java.util.List;
import java.util.Optional;

public interface PublisherService {
    Optional<Publisher> findById(Long id);

    List<Publisher> findAll();

    Optional<Publisher> save(String name, String address);

    void deleteById(Long id);
}
