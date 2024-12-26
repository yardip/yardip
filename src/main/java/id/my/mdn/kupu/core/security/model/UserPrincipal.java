/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.security.model;

import jakarta.security.enterprise.CallerPrincipal;
import java.io.Serializable;


/**
 *
 * @author aphasan
 */
public class UserPrincipal extends CallerPrincipal implements Serializable {    
    
    private final ApplicationUser user;
    
    public UserPrincipal(ApplicationUser user) {
        super(user.getUsername());
        this.user = user;
    }

    @Override
    public String toString() {
        return user.getParty().getName();
    }

    public ApplicationUser getUser() {
        return user;
    }
    
}
