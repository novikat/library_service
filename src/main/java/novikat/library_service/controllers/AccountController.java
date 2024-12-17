package novikat.library_service.controllers;

import novikat.library_service.facades.AccountFacade;
import novikat.library_service.domain.request.CreateAccountRequest;
import novikat.library_service.domain.request.UpdateAccountRequest;
import novikat.library_service.domain.response.AccountResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController {

    private AccountFacade accountFacade;

    public AccountController(AccountFacade accountFacade) {
        this.accountFacade = accountFacade;
    }

    @GetMapping("/all")
    public List<AccountResponse> findAll(){
        return this.accountFacade.findAll();
    }

    @PostMapping("/create")
    public AccountResponse create(@RequestBody final CreateAccountRequest request){
        return this.accountFacade.create(request);
    }

    @PutMapping("/update")
    public AccountResponse update(@RequestBody final UpdateAccountRequest request){
        return this.accountFacade.update(request);
    }
}
