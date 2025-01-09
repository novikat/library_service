package novikat.library_service.services;

import jakarta.ws.rs.core.Response;
import novikat.library_service.domain.enums.Role;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.*;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.GroupRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class KeycloakServiceImpl implements KeycloakService{
    @Value("${keycloak.realm}")
    private String realm;
    private Keycloak keycloak;

    public KeycloakServiceImpl(Keycloak keycloak) {
        this.keycloak = keycloak;
    }

    @Override
    @Transactional
    public void createUser(String username, String email, String password){
        UserRepresentation user = new UserRepresentation();
        user.setEnabled(true);
        user.setUsername(username);
        user.setEmail(email);
        user.setEmailVerified(true);

        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setValue(password);
        credentialRepresentation.setTemporary(false);
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);

        List<CredentialRepresentation> list = new ArrayList<>();
        list.add(credentialRepresentation);
        user.setCredentials(list);

        Response response = this.getUsersResource().create(user);

        if (!Objects.equals(201, response.getStatus())){
            throw new RuntimeException("Error creating keycloak user");
        }
    }

//    @Override
//    @Transactional
//    public void setRole(String username, Role role) {
//        UserResource userResource = this.getUsersResource()
//                .get(this.getUserByUsername(username).getId());
//        List<GroupRepresentation> groups = userResource.groups();
//        String groupId = this.keycloak.realm(realm).getGroupByPath(role.name()).getId();
//        userResource.joinGroup(groupId);
//    }

    @Override
    @Transactional
    public void setRole(String username, Role role) {
        UserResource userResource = this.getUsersResource()
                .get(this.getUserByUsername(username).getId());
        RoleScopeResource roleScopeResource = userResource.roles().realmLevel();
        List<RoleRepresentation> presentRoles = roleScopeResource.listAll().stream()
                .filter(roleRepresentation -> roleRepresentation.getName().startsWith("ROLE_"))
                .toList();
        roleScopeResource.remove(presentRoles);
        roleScopeResource.add(Collections.singletonList(this.getRolesResource().get("ROLE_" + role.name()).toRepresentation()));
    }

    private UsersResource getUsersResource() {
        return this.keycloak.realm(realm).users();
    }
    private RolesResource getRolesResource(){
        return this.keycloak.realm(realm).roles();
    }
    private UserRepresentation getUserByUsername(String username){
        List<UserRepresentation> userRepresentations = this.getUsersResource().searchByUsername(username, true);
        if(!userRepresentations.isEmpty()){
            return userRepresentations.get(0);
        }
        else{
            throw new RuntimeException("User doesn't exist");
        }
    }

    @Override
    public void updateUser(String oldUsername, String newUsername, String newEmail) {
        UserRepresentation userRepresentation = this.getUserByUsername(oldUsername);
        UserResource userResource = this.getUsersResource()
                .get(userRepresentation.getId());
        Optional.ofNullable(newUsername).ifPresent(userRepresentation::setUsername);
        Optional.ofNullable(newEmail).ifPresent(userRepresentation::setEmail);
        userResource.update(userRepresentation);
    }
}
