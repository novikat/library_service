package novikat.library_service.repositories;

import novikat.library_service.domain.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AuthorRepository extends JpaRepository<Author, UUID> {
    boolean existsByFirstNameAndLastName(String firstName, String lastName);
    List<Author> findByLastNameContainingIgnoreCase(String LastName);
    List<Author> findAllByIdIn(List<UUID> authorsId);
}
