package novikat.library_service.domain.models;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

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

    public Review(){}
    private Review(Builder builder) {
        setId(builder.id);
        setVersion(builder.version);
        setBook(builder.book);
        setAccount(builder.account);
        setCreationUtc(builder.creationUtc);
        setTextValue(builder.textValue);
    }

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


    public static final class Builder {
        private UUID id;
        private Integer version;
        private Book book;
        private Account account;
        private Instant creationUtc;
        private String textValue;

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

        public Builder book(Book val) {
            book = val;
            return this;
        }

        public Builder account(Account val) {
            account = val;
            return this;
        }

        public Builder creationUtc(Instant val) {
            creationUtc = val;
            return this;
        }

        public Builder textValue(String val) {
            textValue = val;
            return this;
        }

        public Review build() {
            return new Review(this);
        }
    }
}
