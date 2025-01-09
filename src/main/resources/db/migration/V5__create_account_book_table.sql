CREATE TABLE IF NOT EXISTS account_book (
    account_id UUID NOT NULL,
    book_id UUID NOT NULL,

    CONSTRAINT fk_account FOREIGN KEY (account_id) REFERENCES account(id),
    CONSTRAINT fk_book FOREIGN KEY (book_id) REFERENCES book(id),
    CONSTRAINT uq_account_book UNIQUE(account_id, book_id)
);