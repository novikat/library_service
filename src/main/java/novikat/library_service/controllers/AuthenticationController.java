package novikat.library_service.controllers;

import novikat.library_service.domain.request.AccessTokenRequest;
import novikat.library_service.services.AuthenticationService;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/token")
    public AccessTokenResponse getToken(@RequestBody final AccessTokenRequest request){
        return this.authenticationService.getToken(request);
    }

    @PostMapping("/refresh")
    public AccessTokenResponse refreshToken(@RequestHeader("X-refresh-token") String refreshToken){
        return this.authenticationService.refreshToken(refreshToken);
    }

    @PostMapping("/logout")
    public void logout(@RequestHeader("X-refresh-token") String refreshToken){
        this.authenticationService.logout(refreshToken);
    }
}
