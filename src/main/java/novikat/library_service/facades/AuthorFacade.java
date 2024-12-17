package novikat.library_service.facades;

import novikat.library_service.domain.models.Author;
import novikat.library_service.domain.request.CreateAuthorRequest;
import novikat.library_service.domain.request.UpdateAuthorRequest;
import novikat.library_service.domain.response.AuthorResponse;
import novikat.library_service.domain.response.AuthorShortResponse;
import novikat.library_service.services.AuthorService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;


import java.util.Set;
import java.util.stream.Collectors;
@Component
public class AuthorFacade {
    private AuthorService authorService;

    public AuthorFacade(AuthorService authorService) {
        this.authorService = authorService;
    }
    
    public AuthorResponse addAuthor(CreateAuthorRequest request){
        Author author = this.authorService.addAuthor(request);

        return new AuthorResponse(
                author.getId(),
                author.getFirstName(),
                author.getLastName(),
                author.getBiography()
        );
    }

    public Page<AuthorResponse> getAuthors(Pageable pageable){
        return this.authorService.getAuthors(pageable)
                .map(author -> new AuthorResponse(
                        author.getId(),
                        author.getFirstName(),
                        author.getLastName(),
                        author.getBiography()
                ));
    }

    public Set<AuthorShortResponse> getAuthorsByLastName(String lastName){
        return this.authorService.getAuthorsByLastName(lastName).stream()
                .map(author -> new AuthorShortResponse(
                        author.getId(),
                        author.getFirstName(),
                        author.getLastName()
                ))
                .collect(Collectors.toSet());
    }

    public AuthorResponse updateAuthor(UpdateAuthorRequest request){
        Author author = this.authorService.updateAuthor(request);

        return new AuthorResponse(
                author.getId(),
                author.getFirstName(),
                author.getLastName(),
                author.getBiography()
        );
    }
}
