package novikat.library_service.domain.models;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.Objects;
@Entity
@Table(name = "review")
public class Review extends BaseModel{
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(name = "creation_utc")
    private Instant creationUtc;
    @Column(name = "text_value")
    private String textValue;

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Instant getCreationUtc() {
        return creationUtc;
    }

    public void setCreationUtc(Instant creationUtc) {
        this.creationUtc = creationUtc;
    }

    public String getTextValue() {
        return textValue;
    }

    public void setTextValue(String textValue) {
        this.textValue = textValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Review)) return false;
        if (!super.equals(o)) return false;
        Review review = (Review) o;
        return book.equals(review.book) && account.equals(review.account) && creationUtc.equals(review.creationUtc) && textValue.equals(review.textValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), book, account, creationUtc, textValue);
    }
}
