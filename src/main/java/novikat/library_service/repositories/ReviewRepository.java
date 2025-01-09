package novikat.library_service.repositories;

import novikat.library_service.domain.models.Review;
import novikat.library_service.domain.projection.CommentWithUsernameProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface ReviewRepository extends JpaRepository<Review, UUID> {
    @Query("""
            select new novikat.library_service.domain.projection.CommentWithUsernameProjection(
                r.id,
                r.book.id,
                a.username,
                r.creationUtc,
                r.textValue
            )
            from Review r 
                left join Account a on r.account.id = a.id
            where r.book.id = ?1
            """)
    List<CommentWithUsernameProjection> findAllByBookId(UUID bookId);
}
