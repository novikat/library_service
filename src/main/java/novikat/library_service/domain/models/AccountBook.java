package novikat.library_service.domain.models;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "account_book")
public class AccountBook extends BaseModel{
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    public Book getBook() {
        return book;
    }

    public Account getAuthor() {
        return account;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AccountBook)) return false;
        if (!super.equals(o)) return false;
        AccountBook that = (AccountBook) o;
        return account.equals(that.account) && book.equals(that.book);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), account, book);
    }
}
