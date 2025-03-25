package novikat.library_service.domain.models;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "category")
public class Category extends BaseModel{
    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "categories", fetch = FetchType.LAZY)
    private List<Book> books;
    public Category(){}
    private Category(Builder builder) {
        setId(builder.id);
        setVersion(builder.version);
        setName(builder.name);
        setBooks(builder.books);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Category)) return false;
        if (!super.equals(o)) return false;
        Category category = (Category) o;
        return name.equals(category.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name);
    }


    public static final class Builder {
        private UUID id;
        private Integer version;
        private String name;
        private List<Book> books;

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

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Builder books(List<Book> val) {
            books = val;
            return this;
        }

        public Category build() {
            return new Category(this);
        }
    }
}
