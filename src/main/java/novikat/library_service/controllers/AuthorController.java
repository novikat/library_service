package novikat.library_service.controllers;

import novikat.library_service.domain.response.AuthorWithBooksResponse;
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

import java.util.List;
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

    @GetMapping()
    public AuthorWithBooksResponse findById(@RequestParam final UUID id){
        return this.authorFacade.findById(id);
    }

    @GetMapping("/all")
    public Page<AuthorResponse> findAll(@PageableDefault(value = 20, page = 0, direction = Sort.Direction.ASC) Pageable pageable){
        return this.authorFacade.findAll(pageable);
    }

    @GetMapping("/all/by_lastname")
    public List<AuthorShortResponse> findByLastname(@RequestParam final String lastName){
        return this.authorFacade.findByLastname(lastName);
    }

    @PostMapping()
    public AuthorResponse create(@RequestBody final CreateAuthorRequest request){
        return this.authorFacade.create(request);
    }

    @PutMapping()
    public AuthorResponse update(@RequestBody final UpdateAuthorRequest request){
        return this.authorFacade.update(request);
    }

    @DeleteMapping()
    public void delete(@RequestParam final UUID id){
        this.authorService.delete(id);
    }
}
