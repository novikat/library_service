package novikat.library_service.config;

import com.nimbusds.oauth2.sdk.GrantType;
import novikat.library_service.domain.enums.Role;
import novikat.library_service.security.UserAuthenticationFilter;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Value("${keycloak.admin-client-id}")
    private String adminClientId;
    @Value("${keycloak.admin-client-secret}")
    private String adminClientSecret;
    @Value("${keycloak.server-uri}")
    private String keycloakServerUrl;
    @Value("${keycloak.realm}")
    private String realm;

    private static final String JWT_ISSUER_URI = "http://localhost:8081/realms/library/protocol/openid-connect/certs";

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(new UserAuthenticationFilter(jwtDecoder()), AnonymousAuthenticationFilter.class)
                .authorizeHttpRequests(authorize ->
                        authorize
                                .anyRequest()
                                .permitAll())
//                                .requestMatchers(
//                                        new AntPathRequestMatcher("/admin/review", HttpMethod.DELETE.name())
//                                )
//                                .hasAnyRole(Role.ADMIN.name(), Role.MODERATOR.name())
//                                .requestMatchers(
//                                        new AntPathRequestMatcher("/admin/account", HttpMethod.POST.name()),
//                                        new AntPathRequestMatcher("/admin/account", HttpMethod.PUT.name()),
//                                        new AntPathRequestMatcher("/admin/account/role", HttpMethod.PUT.name()),
//                                        new AntPathRequestMatcher("/admin/book/all", HttpMethod.GET.name()),
//                                        new AntPathRequestMatcher("/admin/review", HttpMethod.DELETE.name()),
//                                        new AntPathRequestMatcher("/book", HttpMethod.POST.name()),
//                                        new AntPathRequestMatcher("/book", HttpMethod.PUT.name()),
//                                        new AntPathRequestMatcher("/book", HttpMethod.DELETE.name()),
//                                        new AntPathRequestMatcher("/author", HttpMethod.POST.name()),
//                                        new AntPathRequestMatcher("/author", HttpMethod.PUT.name()),
//                                        new AntPathRequestMatcher("/author/all", HttpMethod.GET.name()),
//                                        new AntPathRequestMatcher("/author", HttpMethod.DELETE.name()),
//                                        new AntPathRequestMatcher("/category", HttpMethod.GET.name()),
//                                        new AntPathRequestMatcher("/category", HttpMethod.POST.name()),
//                                        new AntPathRequestMatcher("/category", HttpMethod.PUT.name()),
//                                        new AntPathRequestMatcher("/category", HttpMethod.DELETE.name())
//                                )
//                                .hasRole(Role.ADMIN.name())
//                                .requestMatchers(
//                                        new AntPathRequestMatcher( "/account", HttpMethod.GET.name()),
//                                        new AntPathRequestMatcher( "/account", HttpMethod.PUT.name()),
//                                        new AntPathRequestMatcher("/account/favorites", HttpMethod.GET.name()),
//                                        new AntPathRequestMatcher("/account/favorite", HttpMethod.POST.name()),
//                                        new AntPathRequestMatcher("/account/favorite", HttpMethod.DELETE.name())
//                                        )
//                                .hasRole(Role.USER.name())
//                                .requestMatchers(
//                                        new AntPathRequestMatcher("/account", HttpMethod.POST.name()),
//                                        new AntPathRequestMatcher("/auth/**", HttpMethod.POST.name()),
//                                        new AntPathRequestMatcher("/author/all/by_lastname", HttpMethod.GET.name()),
//                                        new AntPathRequestMatcher("/author", HttpMethod.GET.name()),
//                                        new AntPathRequestMatcher("/book/all", HttpMethod.GET.name()),
//                                        new AntPathRequestMatcher("/book", HttpMethod.GET.name()),
//                                        new AntPathRequestMatcher("/category/all", HttpMethod.GET.name()),
//                                        new AntPathRequestMatcher("/review", HttpMethod.POST.name()),
//                                        new AntPathRequestMatcher("/review", HttpMethod.DELETE.name())
//                                        )
//                                .permitAll()
//                                .anyRequest()
//                                .denyAll())
                .build();
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplateBuilder()
                .rootUri("http://localhost:8081")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .build();
    }

    @Bean
    public Keycloak keycloak(){
        return KeycloakBuilder.builder()
                .clientId(adminClientId)
                .clientSecret(adminClientSecret)
                .serverUrl(keycloakServerUrl)
                .realm(realm)
                .grantType(GrantType.CLIENT_CREDENTIALS.getValue())
                .build();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder
                .withJwkSetUri(JWT_ISSUER_URI)
                .jwsAlgorithm(SignatureAlgorithm.RS256)
                .build();
    }
}
