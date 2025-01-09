package novikat.library_service.services;

import novikat.library_service.domain.request.AccessTokenRequest;
import org.keycloak.representations.AccessTokenResponse;

public interface AuthenticationService {

    AccessTokenResponse getToken(AccessTokenRequest request);

    AccessTokenResponse refreshToken(String refreshToken);

    void logout(String refreshToken);
}
