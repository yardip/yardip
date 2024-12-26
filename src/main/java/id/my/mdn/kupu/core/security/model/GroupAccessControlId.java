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
public class GroupAccessControlId implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Long securityGroup;
    
    private Long accessControl;

    public GroupAccessControlId() {
    }

    public GroupAccessControlId(Long securityGroup, Long accessControl) {
        this.securityGroup = securityGroup;
        this.accessControl = accessControl;
    }

    public GroupAccessControlId(String... params) {
        this(Long.parseLong(params[0]), Long.parseLong(params[1]));
    }

    public Long getSecurityGroup() {
        return securityGroup;
    }

    public void setSecurityGroup(Long securityGroup) {
        this.securityGroup = securityGroup;
    }

    public Long getAccessControl() {
        return accessControl;
    }

    public void setAccessControl(Long accessControl) {
        this.accessControl = accessControl;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.securityGroup);
        hash = 89 * hash + Objects.hashCode(this.accessControl);
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
        final GroupAccessControlId other = (GroupAccessControlId) obj;
        if (!Objects.equals(this.accessControl, other.accessControl)) {
            return false;
        }
        if (!Objects.equals(this.securityGroup, other.securityGroup)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return EntityUtil.createStringId(securityGroup, accessControl);
    }
    
}
