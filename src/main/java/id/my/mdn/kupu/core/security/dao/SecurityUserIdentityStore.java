/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.security.dao;

import id.my.mdn.kupu.core.security.model.ApplicationUser;
import id.my.mdn.kupu.core.security.model.UserPrincipal;
import java.util.Optional;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.security.enterprise.identitystore.IdentityStore;

/**
 *
 * @author aphasan
 */
@ApplicationScoped
public class SecurityUserIdentityStore implements IdentityStore {

    @Inject
    private ApplicationUserFacade userService;

    @Override
    public CredentialValidationResult validate(Credential credential) {
        UsernamePasswordCredential login = (UsernamePasswordCredential) credential;
        String username = login.getCaller();
        String password = login.getPasswordAsString();

        Optional<ApplicationUser> user = Optional.ofNullable(
                userService.findByUsernameAndPassword(username, password)
        );

        if (user.isPresent()) {
            return new CredentialValidationResult(
                    new UserPrincipal(user.get()),
                    userService.getGroupsAsString(user.get())
            );
        } else {
            return CredentialValidationResult.INVALID_RESULT;
        }
    }
}
