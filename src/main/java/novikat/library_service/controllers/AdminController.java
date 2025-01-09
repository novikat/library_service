package novikat.library_service.controllers;

import novikat.library_service.domain.request.AdminCreateAccountRequest;
import novikat.library_service.domain.request.SetRoleRequest;
import novikat.library_service.domain.request.UpdateAccountRequest;
import novikat.library_service.domain.response.AccountResponse;
import novikat.library_service.domain.response.BookWithAuthorsResponse;
import novikat.library_service.facades.AccountFacade;
import novikat.library_service.facades.BookFacade;
import novikat.library_service.services.KeycloakService;
import novikat.library_service.services.ReviewService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private AccountFacade accountFacade;

    private KeycloakService keycloakService;
    private ReviewService reviewService;

    private BookFacade bookFacade;

    public AdminController(AccountFacade accountFacade, KeycloakService keycloakService, ReviewService reviewService, BookFacade bookFacade) {
        this.accountFacade = accountFacade;
        this.keycloakService = keycloakService;
        this.reviewService = reviewService;
        this.bookFacade = bookFacade;
    }

    @PostMapping("/account")
    public AccountResponse create(@RequestBody final AdminCreateAccountRequest request){
        return this.accountFacade.create(request);
    }

    @PutMapping("/account")
    public AccountResponse update(@RequestBody final UpdateAccountRequest request){
        return this.accountFacade.update(request);
    }

    @PutMapping("/account/role")
    public void setRole(@RequestBody final SetRoleRequest request){
        this.accountFacade.setRole(request);
    }

    @DeleteMapping("/review")
    public void deleteReview(@RequestParam final @NonNull UUID id){
        this.reviewService.deleteAnyReview(id);
    }

    @GetMapping("/book/all")
    public Page<BookWithAuthorsResponse> findAll(@RequestParam(required = false) String titleLike,
                                                 @RequestParam(required = false) String authorLastNameLike,
                                                 @RequestParam(required = false) Set<UUID> categoriesIn,
                                                 @PageableDefault(value = 20, page = 0) Pageable pageable
    ){
        return this.bookFacade.findAll(titleLike, authorLastNameLike, categoriesIn, pageable, true);
    }
}
