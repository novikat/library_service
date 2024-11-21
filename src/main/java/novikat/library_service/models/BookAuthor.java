package novikat.library_service.models;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.UUID;
@Entity
public class BookAuthor extends BaseModel{

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private Author author;

    public Book getBook() {
        return book;
    }

    public Author getAuthor() {
        return author;
    }
}
