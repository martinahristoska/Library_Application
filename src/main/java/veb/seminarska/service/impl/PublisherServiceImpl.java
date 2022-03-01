package veb.seminarska.service.impl;

import org.springframework.stereotype.Service;
import veb.seminarska.model.Publisher;
import veb.seminarska.repository.jpa.PublisherRepository;
import veb.seminarska.service.PublisherService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class PublisherServiceImpl implements PublisherService {

    private final PublisherRepository publisherRepository;

    public PublisherServiceImpl(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    @Override
    public Optional<Publisher> findById(Long id) {
        return this.publisherRepository.findById(id);
    }

    @Override
    public List<Publisher> findAll() {
        return this.publisherRepository.findAll();
    }

    @Override
    @Transactional
    public Optional<Publisher> save(String name, String address) {


        this.publisherRepository.deleteByName(name);
        return Optional.of(this.publisherRepository.save(new Publisher(name, address)));
    }

    @Override
    public void deleteById(Long id) {
        this.publisherRepository.deleteById(id);
    }
}
