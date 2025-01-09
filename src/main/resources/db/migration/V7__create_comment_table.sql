CREATE TABLE IF NOT EXISTS comment (
    id UUID NOT NULL PRIMARY KEY DEFAULT gen_random_uuid(),
    version INT NOT NULL DEFAULT 0,
    book_id UUID NOT NULL,
    account_id UUID NOT NULL,
    creation_utc TIMESTAMP NOT NULL,
    text_value VARCHAR(500) NOT NULL,

    CONSTRAINT fk_book FOREIGN KEY (book_id) REFERENCES book(id),
    CONSTRAINT fk_account FOREIGN KEY (account_id) REFERENCES account(id),
    CONSTRAINT uq_book_account UNIQUE(book_id, account_id)
);