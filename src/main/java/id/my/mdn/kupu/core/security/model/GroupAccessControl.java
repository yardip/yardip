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
@Table(name = "SECURITY_GROUPACCESSCONTROL")
@NamedQueries({
    @NamedQuery(
            name = "GroupAccessControl.find", 
            query = "SELECT gac FROM GroupAccessControl gac WHERE gac.securityGroup = :group AND gac.accessControl = :acl"
    )
})
public class GroupAccessControl implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @EmbeddedId
    private GroupAccessControlId id;
    
    @MapsId("securityGroup")
    @ManyToOne
    private ApplicationSecurityGroup securityGroup;
    
    @MapsId("accessControl")
    @ManyToOne
    private AccessControl accessControl;

    public GroupAccessControlId getId() {
        return id;
    }

    public void setId(GroupAccessControlId id) {
        this.id = id;
    }

    public ApplicationSecurityGroup getSecurityGroup() {
        return securityGroup;
    }

    public void setSecurityGroup(ApplicationSecurityGroup securityGroup) {
        this.securityGroup = securityGroup;
    }

    public AccessControl getAccessControl() {
        return accessControl;
    }

    public void setAccessControl(AccessControl accessControl) {
        this.accessControl = accessControl;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.id);
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
        final GroupAccessControl other = (GroupAccessControl) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return id.toString();
    }
    
}
