package novikat.library_service.utils;

import org.keycloak.TokenVerifier;
import org.keycloak.common.VerificationException;
import org.keycloak.jose.jwk.JSONWebKeySet;
import org.keycloak.jose.jwk.JWKParser;
import org.keycloak.representations.AccessToken;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class KeycloakTokenVerifier {
    private RestTemplate restTemplate;

    private static final String CERTS_PATH = "/realms/library/protocol/openid-connect/certs";

    public KeycloakTokenVerifier(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void verify(String token) throws VerificationException {
        TokenVerifier<AccessToken> verifier = TokenVerifier.create(token, AccessToken.class);
        ResponseEntity<JSONWebKeySet> response = restTemplate.getForEntity(CERTS_PATH, JSONWebKeySet.class);
        verifier.publicKey(JWKParser.create(response.getBody().getKeys()[1]).toPublicKey());
        verifier.verify();
    }
}
