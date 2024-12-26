/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.security.model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author aphasan
 */
@Entity
@Table(name = "SECURITY_APPLICATIONSECURITYMAP")
@NamedQueries({
    @NamedQuery(
            name = "ApplicationSecurityMap.find", 
            query = "SELECT sm FROM ApplicationSecurityMap sm WHERE sm.group = :group AND sm.user = :user"
    )
})
public class ApplicationSecurityMap implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @EmbeddedId
    private ApplicationSecurityMapId id;
    
    @MapsId("user")
    @ManyToOne
    private ApplicationUser user;
    
    @MapsId("group")
    @ManyToOne
    private ApplicationSecurityGroup group;

    public ApplicationSecurityMapId getId() {
        return id;
    }

    public void setId(ApplicationSecurityMapId id) {
        this.id = id;
    }

    public ApplicationUser getUser() {
        return user;
    }

    public void setUser(ApplicationUser user) {
        this.user = user;
    }

    public ApplicationSecurityGroup getGroup() {
        return group;
    }

    public void setGroup(ApplicationSecurityGroup group) {
        this.group = group;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Objects.hashCode(this.id);
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
        final ApplicationSecurityMap other = (ApplicationSecurityMap) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
}
