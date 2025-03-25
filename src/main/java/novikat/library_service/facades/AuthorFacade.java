package novikat.library_service.facades;

import novikat.library_service.domain.models.Author;
import novikat.library_service.domain.request.CreateAuthorRequest;
import novikat.library_service.domain.request.UpdateAuthorRequest;
import novikat.library_service.domain.response.AuthorResponse;
import novikat.library_service.domain.response.AuthorShortResponse;
import novikat.library_service.domain.response.AuthorWithBooksResponse;
import novikat.library_service.domain.response.BookShortResponse;
import novikat.library_service.services.AuthorService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
@Component
public class AuthorFacade {
    private AuthorService authorService;

    public AuthorFacade(AuthorService authorService) {
        this.authorService = authorService;
    }
    
    public AuthorResponse create(CreateAuthorRequest request){
        Author author = this.authorService.create(request);

        return new AuthorResponse(
                author.getId(),
                author.getFirstName(),
                author.getLastName(),
                author.getBiography()
        );
    }

    public Page<AuthorResponse> findAll(Pageable pageable){
        return this.authorService.findAll(pageable)
                .map(author -> new AuthorResponse(
                        author.getId(),
                        author.getFirstName(),
                        author.getLastName(),
                        author.getBiography()
                ));
    }

    public List<AuthorShortResponse> findByLastname(String lastName){
        return this.authorService.findByLastname(lastName).stream()
                .map(author -> new AuthorShortResponse(
                        author.getId(),
                        author.getFirstName(),
                        author.getLastName()
                ))
                .collect(Collectors.toList());
    }

    public AuthorResponse update(UpdateAuthorRequest request){
        Author author = this.authorService.update(request);

        return new AuthorResponse(
                author.getId(),
                author.getFirstName(),
                author.getLastName(),
                author.getBiography()
        );
    }

    public AuthorWithBooksResponse findById(UUID id) {
        Author author = this.authorService.findById(id);
        return new AuthorWithBooksResponse(
                author.getId(),
                author.getFirstName(),
                author.getLastName(),
                author.getBiography(),
                author.getBooks().stream()
                        .map(book -> new BookShortResponse(book.getId(), book.getTitle()))
                        .collect(Collectors.toList())
        );
    }
}
