/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.security.model;

import id.my.mdn.kupu.core.base.util.EntityUtil;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author aphasan
 */
@Embeddable
public class ApplicationSecurityMapId implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Long user;
    
    private Long group;

    public ApplicationSecurityMapId() {
    }

    public ApplicationSecurityMapId(Long user, Long group) {
        this.user = user;
        this.group = group;
    }

    public ApplicationSecurityMapId(String... params) {
        this(Long.parseLong(params[0]), Long.parseLong(params[1]));
    }

    public Long getUser() {
        return user;
    }

    public void setUser(Long user) {
        this.user = user;
    }

    public Long getGroup() {
        return group;
    }

    public void setGroup(Long group) {
        this.group = group;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + Objects.hashCode(this.user);
        hash = 89 * hash + Objects.hashCode(this.group);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ApplicationSecurityMapId other = (ApplicationSecurityMapId) obj;
        if (!Objects.equals(this.user, other.user)) {
            return false;
        }
        if (!Objects.equals(this.group, other.group)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return EntityUtil.createStringId(user, group);
    }
    
}
