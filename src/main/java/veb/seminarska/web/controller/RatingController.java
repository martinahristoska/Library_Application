package veb.seminarska.web.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import veb.seminarska.model.Book;
import veb.seminarska.service.BookService;
import veb.seminarska.service.RatingService;

@Controller
@RequestMapping("/addRatings")
public class RatingController {
    private final BookService bookService;
    private final RatingService ratingService;

    public RatingController(BookService bookService, RatingService ratingService) {
        this.bookService = bookService;
        this.ratingService = ratingService;
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getRatingsPage(@PathVariable Long id, Model model) {
        model.addAttribute("ratings", ratingService.findAll());
        model.addAttribute("id", id);
        Book book = bookService.findById(id);
        model.addAttribute("book", book);
        model.addAttribute("bodyContent", "rateBook");
        return "master-template";
    }

    @PostMapping
    public String addRatingForBook(@RequestParam Long bookId,
                                   @RequestParam Long ratingId) {
        Book book = bookService.addRating(ratingId, bookId);
        return "redirect:/books";
    }
}
