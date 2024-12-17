package novikat.library_service.controllers;

import novikat.library_service.facades.AuthorFacade;
import novikat.library_service.domain.request.CreateAuthorRequest;
import novikat.library_service.domain.request.UpdateAuthorRequest;
import novikat.library_service.domain.response.AuthorResponse;
import novikat.library_service.domain.response.AuthorShortResponse;
import novikat.library_service.services.AuthorService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/author")
public class AuthorController {

    private AuthorFacade authorFacade;
    private AuthorService authorService;

    public AuthorController(AuthorFacade authorFacade, AuthorService authorService) {
        this.authorFacade = authorFacade;
        this.authorService = authorService;
    }

    @GetMapping("/all")
    public Page<AuthorResponse> getAuthors(@PageableDefault(value = 20, page = 0, direction = Sort.Direction.ASC) Pageable pageable){
        return this.authorFacade.getAuthors(pageable);
    }

    @GetMapping("/all/by_lastname")
    public Set<AuthorShortResponse> getAuthorsByLastName(@RequestParam final String lastName){
        return this.authorFacade.getAuthorsByLastName(lastName);
    }

    @PostMapping("/add")
    public AuthorResponse addAuthor(@RequestBody final CreateAuthorRequest request){
        return this.authorFacade.addAuthor(request);
    }

    @PutMapping("/update")
    public AuthorResponse updateAuthor(@RequestBody final UpdateAuthorRequest request){
        return this.authorFacade.updateAuthor(request);
    }

    @DeleteMapping("/delete")
    public void deleteAuthor(@RequestParam final UUID id){
        this.authorService.deleteAuthor(id);
    }
}
