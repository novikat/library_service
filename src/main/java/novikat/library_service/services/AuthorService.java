package novikat.library_service.services;

import novikat.library_service.domain.models.Author;
import novikat.library_service.domain.request.CreateAuthorRequest;
import novikat.library_service.domain.request.UpdateAuthorRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Set;
import java.util.UUID;

public interface AuthorService {
    Author create(CreateAuthorRequest request);
    Page<Author> findAll(Pageable pageable);
    Set<Author> findByLastname(String lastName);
    Author update(UpdateAuthorRequest request);
    Author findById(UUID id);
    Set<Author> findByIdIn(Set<UUID> authorsId);

    void delete(UUID id);
}
