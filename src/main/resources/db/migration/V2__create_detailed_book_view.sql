CREATE OR REPLACE VIEW detailed_book_view AS
    SELECT
        b.id            AS book_id,
        b.title         AS book_title,
        c.id            AS category_id,
        c.name          AS category_name,
        a.id            AS author_id,
        a.first_name    AS author_first_name,
        a.last_name     AS author_last_name
    FROM book b
        LEFT JOIN book_category bc on b.id = bc.book_id
        LEFT JOIN "category" c on bc.category_id = c.id
        LEFT JOIN book_author ba ON b.id = ba.book_id
        LEFT JOIN author a on ba.author_id = a.id;
