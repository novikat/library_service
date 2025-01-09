package novikat.library_service.domain.models;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.Set;

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
    private Set<Book> favoriteBooks;

    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
    private Set<AccountBook> accountBooks;


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
    public Set<Book> getFavoriteBooks() {
        return favoriteBooks;
    }
//
//    public void setFavoriteBooks(Set<Book> favoriteBooks) {
//        this.favoriteBooks = favoriteBooks;
//    }

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
}
