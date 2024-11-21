package novikat.library_service.services;

import novikat.library_service.models.Author;
import novikat.library_service.models.request.CreateAuthorRequest;
import novikat.library_service.models.request.UpdateAuthorRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Set;
import java.util.UUID;

public interface AuthorService {
    Author addAuthor(CreateAuthorRequest request);
    Page<Author> getAuthors(Pageable pageable);
    Set<Author> getAuthorsByLastName(String lastName);
    Author updateAuthor(UpdateAuthorRequest request);
    Author getAuthorById(UUID id);
    Set<Author> getAuthorsByIdIn(Set<UUID> authorsId);
}
