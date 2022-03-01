package veb.seminarska.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import veb.seminarska.model.enumerations.RentalStatus;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Rentals {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dateCreated;

    @ManyToOne
    private User user;

    @Enumerated(EnumType.STRING)
    private RentalStatus status;

    @ManyToMany
    private List<Book> books;


    public Rentals(User user, List<Book> books) {
        this.user = user;
        this.books = books;
    }


    public Rentals(User user) {
        this.user = user;
        this.dateCreated = LocalDateTime.now();
        this.books = new ArrayList<>();
        this.status = RentalStatus.CREATED;

    }
}