package veb.seminarska.web.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import veb.seminarska.model.Category;
import veb.seminarska.service.CategoryService;

import java.util.List;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public String getCategoriesPage(@RequestParam(required = false) String error, Model model) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        List<Category> categories = this.categoryService.listCategories();
        model.addAttribute("categories", categories);
        model.addAttribute("bodyContent", "categories");
        return "master-template";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteCategory(@PathVariable Long id) {
        this.categoryService.deleteById(id);
        return "redirect:/categories";
    }


    @GetMapping("/edit-form/{id}")
    public String editCategoryPage(@PathVariable Long id, Model model) {
        if (this.categoryService.findById(id).isPresent()) {
            Category category = this.categoryService.findById(id).get();
            model.addAttribute("category", category);
            model.addAttribute("bodyContent", "add-category");
            return "master-template";
        }
        return "redirect:/category?error=CategoryNotFound";
    }

    @GetMapping("/add-form")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addCategoryPage(Model model) {
        model.addAttribute("bodyContent", "add-category");
        return "master-template";
    }

    @PostMapping("/add")
    public String saveCategory(@RequestParam String name,
                               @RequestParam String description
    ) {
        this.categoryService.save(name, description);
        return "redirect:/categories";
    }
}
