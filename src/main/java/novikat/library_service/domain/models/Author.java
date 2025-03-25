package novikat.library_service.domain.models;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "author")
public class Author extends BaseModel {
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "biography")
    private String biography;
    @ManyToMany(mappedBy = "authors", fetch = FetchType.LAZY)
    private List<Book> books;
    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
    private List<BookAuthor> bookAuthor;

    public Author() {
    }

    private Author(Builder builder) {
        setFirstName(builder.firstName);
        setLastName(builder.lastName);
        setBiography(builder.biography);
        setBooks(builder.books);
        setBookAuthor(builder.bookAuthor);
        setId(builder.id);
        setVersion(builder.version);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public List<BookAuthor> getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(List<BookAuthor> bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Author)) return false;
        if (!super.equals(o)) return false;
        Author author = (Author) o;
        return firstName.equals(author.firstName) && lastName.equals(author.lastName) && Objects.equals(biography, author.biography);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), firstName, lastName, biography);
    }


    public static final class Builder {
        private String firstName;
        private String lastName;
        private String biography;
        private List<Book> books;
        private List<BookAuthor> bookAuthor;
        private UUID id;
        private Integer version;

        private Builder() {
        }

        public static Builder builder() {
            return new Builder();
        }

        public Builder firstName(String val) {
            firstName = val;
            return this;
        }

        public Builder lastName(String val) {
            lastName = val;
            return this;
        }

        public Builder biography(String val) {
            biography = val;
            return this;
        }

        public Builder books(List<Book> val) {
            books = val;
            return this;
        }

        public Builder bookAuthor(List<BookAuthor> val) {
            bookAuthor = val;
            return this;
        }

        public Builder id(UUID val) {
            id = val;
            return this;
        }

        public Builder version(Integer val) {
            version = val;
            return this;
        }

        public Author build() {
            return new Author(this);
        }
    }
}
