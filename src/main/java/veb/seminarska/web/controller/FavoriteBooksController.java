package veb.seminarska.web.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import veb.seminarska.model.FavoriteBooks;
import veb.seminarska.model.User;
import veb.seminarska.service.FavoriteBooksService;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/favorite-books")
public class FavoriteBooksController {

    private final FavoriteBooksService favoriteBooksService;

    public FavoriteBooksController(FavoriteBooksService favoriteBooksService) {
        this.favoriteBooksService = favoriteBooksService;
    }

    @GetMapping
    public String getFavoriteBooksPage(@RequestParam(required = false) String error,
                                       HttpServletRequest req,
                                       Model model) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        String username = req.getRemoteUser();
        FavoriteBooks booksToRead = this.favoriteBooksService.getActiveMyFavoriteBooks(username);
        model.addAttribute("books", this.favoriteBooksService.listAllBooksInFavoriteBooks(booksToRead.getId()));
        model.addAttribute("bodyContent", "favorite-books");
        return "master-template";
    }

    @PostMapping("/add-book/{id}")
    public String addBookToFavoriteBooks(@PathVariable Long id, HttpServletRequest req, Authentication authentication) {
        try {
            User user = (User) authentication.getPrincipal();
            this.favoriteBooksService.addBookToMyFavoriteBooks(user.getUsername(), id);
            return "redirect:/favorite-books";
        } catch (RuntimeException exception) {
            return "redirect:/favorite-books?error=" + exception.getMessage();
        }
    }
}