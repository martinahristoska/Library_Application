package veb.seminarska.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import veb.seminarska.model.*;
import veb.seminarska.repository.jpa.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/charts")
public class ChartController {
    private final BookRepository bookRepository;
    private final RatingRepository ratingRepository;
    private final CategoryRepository categoryRepository;

    private final PublisherRepository publisherRepository;

    public ChartController(BookRepository bookRepository, RatingRepository ratingRepository,  CategoryRepository categoryRepository, PublisherRepository publisherRepository) {
        this.bookRepository = bookRepository;
        this.ratingRepository = ratingRepository;
        this.categoryRepository = categoryRepository;
        this.publisherRepository = publisherRepository;
    }

    @GetMapping
    public String getChartsPage(@RequestParam(required = false) String error, Model model) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        model.addAttribute("bodyContent", "charts");
        return "master-template";
    }


    @GetMapping("/categoryChart")
    public String getCategoryChart(Model model) {
        List<Category> categories = categoryRepository.findAll();
        Map<String, Integer> map = new LinkedHashMap<>();

        for(Category category : categories) {
            List<Book> categoryBooks = bookRepository.findByCategory(category);
            int numBooksByAuthor = categoryBooks.size();
            String name = category.getName();
            map.put(name, numBooksByAuthor);
        }
        model.addAttribute("map", map);
        model.addAttribute("bodyContent", "categoriesChart");
        return "master-template";
    }

    @GetMapping("/publisherChart")
    public String getPublisherChart(Model model) {
        List<Publisher> publishers = publisherRepository.findAll();
        Map<String, Integer> map = new LinkedHashMap<>();

        for(Publisher publisher : publishers) {
            List<Book> publisherBooks = bookRepository.findByPublisher(publisher);
            int numBooksPublisher = publisherBooks.size();
            String name = publisher.getName();
            map.put(name, numBooksPublisher);
        }
        model.addAttribute("map", map);
        model.addAttribute("bodyContent", "publishersChart");
        return "master-template";
    }


    @GetMapping("/ratingsChart")
    public String getRatingsChart(Model model) {
        Map<String, Integer> bookMap = new LinkedHashMap<>();

        List<Book> books = bookRepository.findAll();
        for(Book book : books) {
            List<Rating> ratings = book.getRatings();
            int max = 0;
            for(Rating rating : ratings) {
                if(rating.getNumber() > max) {
                    max = rating.getNumber();
                }
            }
            String name = book.getTitle();
            bookMap.put(name, max);
        }
        model.addAttribute("bookMap", bookMap);
        model.addAttribute("bodyContent", "ratingsChart");
        return "master-template";
    }

}
