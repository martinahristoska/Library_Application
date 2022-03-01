package veb.seminarska.web.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import veb.seminarska.model.Rentals;
import veb.seminarska.model.User;
import veb.seminarska.service.RentalService;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/rental")
public class RentalController {

    private final RentalService rentalService;

    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @GetMapping
    public String getRentalsPage(@RequestParam(required = false) String error,
                                 HttpServletRequest req,
                                 Model model) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        String username = req.getRemoteUser();
        Rentals booksToRead = this.rentalService.getActiveRentals(username);
        model.addAttribute("books", this.rentalService.listAllBooksInMyRentals(booksToRead.getId()));
        model.addAttribute("bodyContent", "rental");
        return "master-template";
    }

    @PostMapping("/add-book/{id}")
    public String addRental(@PathVariable Long id, HttpServletRequest req, Authentication authentication) {
        try {
            User user = (User) authentication.getPrincipal();
            this.rentalService.addBookToRentals(user.getUsername(), id);
            return "redirect:/rental";
        } catch (RuntimeException exception) {
            return "redirect:/rental?error=" + exception.getMessage();
        }
    }
}
