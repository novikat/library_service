package novikat.library_service.domain.models;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "account")
public class Account extends BaseModel{
    @Column(name = "username")
    private String username;
    @Column(name = "email")
    private String email;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
            name = "account_book",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private List<Book> favoriteBooks;

    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
    private List<AccountBook> accountBooks;

    private Account(Builder builder) {
        setUsername(builder.username);
        setEmail(builder.email);
        setFirstName(builder.firstName);
        setLastName(builder.lastName);
        favoriteBooks = builder.favoriteBooks;
        accountBooks = builder.accountBooks;
        setId(builder.id);
        setVersion(builder.version);
    }

    public Account() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
    public List<Book> getFavoriteBooks() {
        return favoriteBooks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account)) return false;
        if (!super.equals(o)) return false;
        Account account = (Account) o;
        return username.equals(account.username) && email.equals(account.email) && Objects.equals(firstName, account.firstName) && Objects.equals(lastName, account.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), username, email, firstName, lastName);
    }

    public static final class Builder {
        private String username;
        private String email;
        private String firstName;
        private String lastName;
        private List<Book> favoriteBooks;
        private List<AccountBook> accountBooks;
        private UUID id;
        private Integer version;

        private Builder() {
        }

        public static Builder builder() {
            return new Builder();
        }

        public Builder username(String val) {
            username = val;
            return this;
        }

        public Builder email(String val) {
            email = val;
            return this;
        }

        public Builder firstName(String val) {
            firstName = val;
            return this;
        }

        public Builder lastName(String val) {
            lastName = val;
            return this;
        }

        public Builder favoriteBooks(List<Book> val) {
            favoriteBooks = val;
            return this;
        }

        public Builder accountBooks(List<AccountBook> val) {
            accountBooks = val;
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

        public Account build() {
            return new Account(this);
        }
    }
}
