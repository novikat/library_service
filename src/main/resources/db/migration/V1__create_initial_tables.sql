CREATE TABLE IF NOT EXISTS account (
  id UUID NOT NULL PRIMARY KEY,
  version INT NOT NULL,
  username VARCHAR(40) NOT NULL,
  email VARCHAR(200) NOT NULL,
  first_name VARCHAR(40),
  last_name VARCHAR(40),

  CONSTRAINT uq_username UNIQUE(username),
  CONSTRAINT uq_email UNIQUE(email)
);

CREATE TABLE IF NOT EXISTS author (
    id UUID NOT NULL PRIMARY KEY,
    version INT NOT NULL,
    first_name VARCHAR(40),
    last_name VARCHAR(40),
    biography TEXT
);

CREATE TABLE IF NOT EXISTS book (
    id UUID NOT NULL PRIMARY KEY,
    version INT NOT NULL,
    title VARCHAR(50),
    description TEXT
);

CREATE TABLE IF NOT EXISTS category (
    id UUID NOT NULL PRIMARY KEY,
    version INT NOT NULL,
    name VARCHAR(40),

    CONSTRAINT uq_name UNIQUE(name)
);

CREATE TABLE IF NOT EXISTS shelf (
    id UUID NOT NULL PRIMARY KEY,
    version INT NOT NULL,
    name VARCHAR(40),
    account_id UUID,

    CONSTRAINT fk_account FOREIGN KEY(account_id) REFERENCES account(id)
);

CREATE TABLE IF NOT EXISTS book_author (
    id UUID NOT NULL PRIMARY KEY DEFAULT gen_random_uuid(),
    version INT NOT NULL DEFAULT 0,
    book_id UUID NOT NULL,
    author_id UUID NOT NULL,

    CONSTRAINT fk_book FOREIGN KEY (book_id) REFERENCES book(id),
    CONSTRAINT fk_author FOREIGN KEY (author_id) REFERENCES author(id),
    CONSTRAINT uq_book_author UNIQUE(book_id, author_id)
);

CREATE TABLE IF NOT EXISTS book_category (
    book_id UUID NOT NULL,
    category_id UUID NOT NULL,

    CONSTRAINT fk_book FOREIGN KEY (book_id) REFERENCES book(id),
    CONSTRAINT fk_category FOREIGN KEY (category_id) REFERENCES category(id),
    CONSTRAINT uq_book_category UNIQUE(book_id, category_id)
);

CREATE TABLE IF NOT EXISTS shelf_book (
    shelf_id UUID NOT NULL,
    book_id UUID NOT NULL,

    CONSTRAINT fk_shelf FOREIGN KEY (shelf_id) REFERENCES shelf(id),
    CONSTRAINT fk_book FOREIGN KEY (book_id) REFERENCES book(id),
    CONSTRAINT uq_shelf_book UNIQUE(shelf_id, book_id)
);