package veb.seminarska.web.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.spring5.SpringTemplateEngine;
import veb.seminarska.service.RatingService;

import javax.servlet.ServletException;
import java.io.IOException;

@Controller
@RequestMapping("/createRating")
public class CreateRatingController {
    private final RatingService ratingService;
    private final SpringTemplateEngine springTemplateEngine;

    public CreateRatingController(RatingService ratingService, SpringTemplateEngine springTemplateEngine) {
        this.ratingService = ratingService;
        this.springTemplateEngine = springTemplateEngine;
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addRating(Model model) throws ServletException, IOException {
        model.addAttribute("bodyContent", "addNewRating");
        return "master-template";
    }

    @PostMapping
    public String createNewRating(@RequestParam Integer number) {
        ratingService.save(number);
        return "redirect:/books";
    }
}
