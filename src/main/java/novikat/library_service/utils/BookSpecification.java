package novikat.library_service.utils;

import jakarta.persistence.criteria.Join;
import novikat.library_service.domain.models.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class BookSpecification {
    public static Specification<Book> titleLike(String title){
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(
                        criteriaBuilder.lower(root.get(Book_.TITLE)),
                        "%" + title.toLowerCase() + "%");
    }

    public static Specification<Book> authorLastNameLike(String authorLastName){
        return (root, query, criteriaBuilder) -> {
            Join<Book, Author> bookAuthor = root.join(Book_.AUTHORS);
            return criteriaBuilder.like(
                    criteriaBuilder.lower(bookAuthor.get(Author_.LAST_NAME)),
                    "%"+ authorLastName.toLowerCase() + "%");
        };
    }

    public static Specification<Book> categoryIdIn(Set<UUID> categories){
        return (root, query, criteriaBuilder) -> {
            Join<Book, Category> bookCategory = root.join(Book_.CATEGORIES);
            return bookCategory.get(Category_.ID).in(categories);
        };
    }

    public static Specification<Book> isNotDeleted(){
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(Book_.DELETED), false));
    }
}
