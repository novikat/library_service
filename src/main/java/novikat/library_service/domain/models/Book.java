package novikat.library_service.domain.models;

import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.SQLDelete;

import java.util.Objects;
import java.util.List;
import java.util.UUID;

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
    private List<Author> authors;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
            name = "book_category",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> categories;

    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY)
    private List<BookAuthor> bookAuthor;


    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY)
    private List<AccountBook> bookAccounts;

    public Book(){}
    private Book(Builder builder) {
        setId(builder.id);
        setVersion(builder.version);
        setTitle(builder.title);
        setDescription(builder.description);
        setDeleted(builder.deleted);
        setAuthors(builder.authors);
        setCategories(builder.categories);
        setBookAuthor(builder.bookAuthor);
        bookAccounts = builder.bookAccounts;
    }

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

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<BookAuthor> getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(List<BookAuthor> bookAuthor) {
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


    public static final class Builder {
        private UUID id;
        private Integer version;
        private String title;
        private String description;
        private boolean deleted;
        private List<Author> authors;
        private List<Category> categories;
        private List<BookAuthor> bookAuthor;
        private List<AccountBook> bookAccounts;

        private Builder() {
        }

        public static Builder builder() {
            return new Builder();
        }

        public Builder id(UUID val) {
            id = val;
            return this;
        }

        public Builder version(Integer val) {
            version = val;
            return this;
        }

        public Builder title(String val) {
            title = val;
            return this;
        }

        public Builder description(String val) {
            description = val;
            return this;
        }

        public Builder deleted(boolean val) {
            deleted = val;
            return this;
        }

        public Builder authors(List<Author> val) {
            authors = val;
            return this;
        }

        public Builder categories(List<Category> val) {
            categories = val;
            return this;
        }

        public Builder bookAuthor(List<BookAuthor> val) {
            bookAuthor = val;
            return this;
        }

        public Builder bookAccounts(List<AccountBook> val) {
            bookAccounts = val;
            return this;
        }

        public Book build() {
            return new Book(this);
        }
    }
}
