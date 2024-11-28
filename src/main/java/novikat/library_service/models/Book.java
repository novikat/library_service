package novikat.library_service.models;

import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.SQLDelete;

import java.util.Objects;
import java.util.Set;
@Entity
@Table(name = "book")
@SQLDelete(sql = "UPDATE book SET deleted = true WHERE id=? AND version = ?")
public class Book extends BaseModel{

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "deleted")
    private boolean deleted;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @Fetch(FetchMode.SUBSELECT)
    @JoinTable(
            name = "book_author",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private Set<Author> authors;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
            name = "book_category",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories;

    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY)
    private Set<BookAuthor> bookAuthor;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public Set<BookAuthor> getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(Set<BookAuthor> bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        if (!super.equals(o)) return false;
        Book book = (Book) o;
        return title.equals(book.title) && Objects.equals(description, book.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), title, description);
    }
}
