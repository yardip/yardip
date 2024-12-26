/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.security.util;

import id.my.mdn.kupu.core.security.dao.ApplicationUserFacade;
import id.my.mdn.kupu.core.security.model.ApplicationUser;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.security.enterprise.SecurityContext;
import java.security.Principal;
import java.util.Collections;
import java.util.Set;

/**
 *
 * @author aphasan
 */
@Named(value = "rolesChecker")
@RequestScoped
public class RolesChecker {

    @Inject
    private SecurityContext securityContext;

    @Inject
    private ApplicationUserFacade userFacade;

    public boolean isUserHasRoles(String... roles) {
        if (roles.length == 0) {
            return true;
        }

        Set<String> acls = getUserPermissions();
        if (acls.contains("manage_universe")) {
            return true;
        }
        return acls.containsAll(Set.of(roles));
    }

    public Set<String> getUserPermissions() {
        Principal principal = securityContext.getCallerPrincipal();
        if (principal != null) {
            ApplicationUser user = userFacade.findByUsername(principal.getName());

            if (user != null) {
                return userFacade.getGroupsAsString(user);
            }
        }

        return Collections.EMPTY_SET;
    }

}
