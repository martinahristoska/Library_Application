package veb.seminarska.web.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import veb.seminarska.model.Publisher;
import veb.seminarska.service.PublisherService;

import java.util.List;

@Controller
@RequestMapping("/publishers")
public class PublisherController {

    private final PublisherService publisherService;

    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @GetMapping
    public String getPublishersPage(@RequestParam(required = false) String error, Model model) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        List<Publisher> publishers = this.publisherService.findAll();
        model.addAttribute("publishers", publishers);
        model.addAttribute("bodyContent", "publishers");
        return "master-template";
    }

    @DeleteMapping("/delete/{id}")
    public String deletePublisher(@PathVariable Long id) {
        this.publisherService.deleteById(id);
        return "redirect:/publishers";
    }


    @GetMapping("/add-form")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addPublisherPage(Model model) {
        model.addAttribute("bodyContent", "add-publisher");
        return "master-template";
    }

    @GetMapping("/edit-form/{id}")
    public String editPublisherPage(@PathVariable Long id, Model model) {
        if (this.publisherService.findById(id).isPresent()) {
            Publisher publisher = this.publisherService.findById(id).get();
            model.addAttribute("publisher", publisher);
            model.addAttribute("bodyContent", "add-publisher");
            return "master-template";
        }
        return "redirect:/publisher?error=PublisherNotFound";
    }

    @PostMapping("/add")
    public String savePublisher(@RequestParam String name,
                                @RequestParam String address
    ) {
        this.publisherService.save(name, address);
        return "redirect:/publishers";
    }
}
