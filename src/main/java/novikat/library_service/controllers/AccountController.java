package novikat.library_service.controllers;

import novikat.library_service.domain.request.EditFavoriteRequest;
import novikat.library_service.domain.response.BookWithAuthorsResponse;
import novikat.library_service.facades.AccountFacade;
import novikat.library_service.domain.request.CreateAccountRequest;
import novikat.library_service.domain.request.UpdateAccountRequest;
import novikat.library_service.domain.response.AccountResponse;
import novikat.library_service.services.AccountService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/account")
public class AccountController {

    private AccountFacade accountFacade;
    private AccountService accountService;

    public AccountController(AccountFacade accountFacade, AccountService accountService) {
        this.accountFacade = accountFacade;
        this.accountService = accountService;
    }

    @GetMapping("/all")
    public List<AccountResponse> findAll(){
        return this.accountFacade.findAll();
    }

    @PostMapping()
    public AccountResponse create(@RequestBody final CreateAccountRequest request){
        return this.accountFacade.create(request);
    }

    @PutMapping()
    public AccountResponse update(@RequestBody final UpdateAccountRequest request){
        return this.accountFacade.update(request);
    }

    @GetMapping()
    public AccountResponse findById(@RequestParam final UUID id){
        return this.accountFacade.findById(id);
    }

    @GetMapping("/favorites")
    public Page<BookWithAuthorsResponse> getFavoriteBooks(@RequestParam final UUID accountId){
        return this.accountFacade.getFavoriteBooks(accountId);
    }

    @PostMapping("/favorite")
    public void addFavoriteBook(@RequestBody final EditFavoriteRequest request){
        this.accountFacade.addFavoriteBook(request);
    }

    @DeleteMapping("/favorite")
    public void removeFavoriteBook(@RequestBody final EditFavoriteRequest request){
        this.accountFacade.removeFavoriteBook(request);
    }
}
