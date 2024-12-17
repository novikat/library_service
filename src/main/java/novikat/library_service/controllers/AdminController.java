package novikat.library_service.controllers;

import novikat.library_service.domain.request.AdminCreateAccountRequest;
import novikat.library_service.domain.request.SetRoleRequest;
import novikat.library_service.domain.request.UpdateAccountRequest;
import novikat.library_service.domain.response.AccountResponse;
import novikat.library_service.facades.AccountFacade;
import novikat.library_service.services.KeycloakService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private AccountFacade accountFacade;

    private KeycloakService keycloakService;

    public AdminController(AccountFacade accountFacade, KeycloakService keycloakService) {
        this.accountFacade = accountFacade;
        this.keycloakService = keycloakService;
    }

    @PostMapping("/account/create")
    public AccountResponse create(@RequestBody final AdminCreateAccountRequest request){
        return this.accountFacade.create(request);
    }

    @PutMapping("/account/update")
    public AccountResponse update(@RequestBody final UpdateAccountRequest request){
        return this.accountFacade.update(request);
    }

    @PutMapping("/account/set_role")
    public void setRole(@RequestBody final SetRoleRequest request){
        this.keycloakService.setRole(request.username(), request.role());
    }
}
