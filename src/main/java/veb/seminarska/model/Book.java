package veb.seminarska.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String authorName;

    @ManyToOne
    private Category category;

    @ManyToOne
    private Publisher publisher;

    @ManyToMany
    private List<Rating> ratings;

    @Column(columnDefinition = "text")
    private String cover;

    public Book(String title, String authorName, Category category, Publisher publisher, String cover) {
        this.title = title;
        this.authorName = authorName;
        this.category = category;
        this.publisher = publisher;
        this.cover = cover;
    }

    public Book(String title, String authorName, Category category, Publisher publisher) {
        this.title = title;
        this.authorName = authorName;
        this.category = category;
        this.publisher = publisher;
    }
}