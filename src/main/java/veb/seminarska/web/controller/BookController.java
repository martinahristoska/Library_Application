package veb.seminarska.web.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import veb.seminarska.model.Book;
import veb.seminarska.model.Category;
import veb.seminarska.model.Publisher;
import veb.seminarska.service.BookService;
import veb.seminarska.service.CategoryService;
import veb.seminarska.service.PublisherService;

import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final CategoryService categoryService;
    private final PublisherService publisherService;

    public BookController(BookService bookService, CategoryService categoryService, PublisherService publisherService) {
        this.bookService = bookService;
        this.categoryService = categoryService;
        this.publisherService = publisherService;
    }

    @GetMapping
    public String getBooksPage(@RequestParam(required = false) String error,
                               @RequestParam (required = false) String nameSearch,
                               Model model) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        List<Book> books = this.bookService.findAll();
        List<Category> categories = categoryService.listCategories();
        if (nameSearch == null) {
            books =  this.bookService.findAll();
        } else {
            books = this.bookService.listBooksByTitle(nameSearch);
        }
        model.addAttribute("books", books);
        model.addAttribute("categories", categories);
        model.addAttribute("bodyContent", "books");
        return "master-template";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        this.bookService.deleteById(id);
        return "redirect:/books";
    }

    @GetMapping("/edit-form/{id}")
    public String editBookPage(@PathVariable Long id, Model model) {
        if (this.bookService.findById(id)!=null) {
            Book book = this.bookService.findById(id);
            List<Publisher> publishers = this.publisherService.findAll();
            List<Category> categories = this.categoryService.listCategories();
            model.addAttribute("publishers", publishers);
            model.addAttribute("categories", categories);
            model.addAttribute("book", book);
            model.addAttribute("bodyContent", "edit-book");
            return "master-template";
        }
        return "redirect:/books?error=BookNotFound";
    }

    @GetMapping("/add-form")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addBookPage(Model model) {
        List<Publisher> publishers = this.publisherService.findAll();
        List<Category> categories = this.categoryService.listCategories();
        model.addAttribute("publishers", publishers);
        model.addAttribute("categories", categories);
        model.addAttribute("bodyContent", "add-book");
        return "master-template";
    }

    @PostMapping("/add")
    public String saveBook(@RequestParam String title,
                           @RequestParam String authorName,
                           @RequestParam Long category,
                           @RequestParam Long publisher,
                           @RequestParam String cover) {
        this.bookService.save(title, authorName, category, publisher, cover);
        return "redirect:/books";
    }
    @PostMapping("/edit/{id}")
    public String updateBook(@PathVariable Long id,
                             @RequestParam String title,
                             @RequestParam String authorName,
                             @RequestParam Long category,
                             @RequestParam Long publisher,
                             @RequestParam String cover) {
        this.bookService.update(id, title, authorName, category, publisher, cover);
        return "redirect:/books";
    }
}
