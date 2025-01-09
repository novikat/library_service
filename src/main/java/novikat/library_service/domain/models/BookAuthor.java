package novikat.library_service.domain.models;

import jakarta.persistence.*;

@Entity
@Table(name = "book_author")
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
