/*
 * Copyright 2015 Arief Prihasanto <ariefp5758 at gmail.com>.
 * All rights reserved

 * A lot of time, effort and money is spent designing and implementing the software.
 * All system design, text, graphics, the selection and arrangement thereof, and
 * all software compilations, underlying source code, software and all other material
 * on this software are copyright Arief Prihasanto <ariefp5758 at gmail.com> and any affiliates.
 * 
 * In simple terms, every element of this software is protected by copyright.
 * Unless you have our express written permission, you are not allowed
 * to copy partially and or completely, modify partially and or completely,
 * use partially and or completely and or reproduce any part of this  software
 * in any way, shape and or form.
 * 
 * Taking material from other source code and or document Arief Prihasanto <ariefp5758 at gmail.com> and affiliates has designed is
 * also prohibited. You can be prosecuted by the licensee as well as by us as licensor.
 * 
 * Any other use of materials of this software, including reproduction for purposes other
 * than that noted in the business agreement, modification, distribution, or republication,
 * without the prior written permission of Arief Prihasanto <ariefp5758 at gmail.com> is strictly prohibited.
 * 
 * The source code, partially and or completely, shall not be presented and or shown
 * and or performed to public and or other parties without the prior written permission
 * of Arief Prihasanto <ariefp5758 at gmail.com>

 */
package id.my.mdn.kupu.core.security.model;

import id.my.mdn.kupu.core.base.model.EntityBuilder;
import id.my.mdn.kupu.core.party.entity.Organization;
import id.my.mdn.kupu.core.party.entity.PartyRole;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.persistence.SqlResultSetMappings;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import static java.util.stream.Collectors.joining;

/**
 *
 * @author Arief Prihasanto <ariefp5758 at gmail.com>
 */
@Entity
@Table(name = "SECURITY_APPLICATIONSECURITYGROUP")
//@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"GROUPNAME", "MODULE_NAME"}))
@SqlResultSetMappings({
    @SqlResultSetMapping(
            name = "ApplicationSecurityGroup",
            classes = {
                @ConstructorResult(
                        targetClass = ApplicationSecurityGroup.class,
                        columns = {
                            @ColumnResult(name = "ID", type = Long.class),
                            @ColumnResult(name = "ORGID", type = Long.class),
                            @ColumnResult(name = "GROUPNAME", type = String.class),
                            @ColumnResult(name = "ACTIVE", type = Boolean.class)
                        }
                )
            }
    )
})
public class ApplicationSecurityGroup extends PartyRole {

    private static final long serialVersionUID = 1L;
    
    public static final Builder builder() {
        return new Builder();
    }

    public static final class Builder extends EntityBuilder<ApplicationSecurityGroup> {

        public Builder() {
            super(new ApplicationSecurityGroup());
        }

        public Builder organization(Organization organization) {
            if (organization.getRoles() == null) {
                organization.setRoles(new ArrayList<>());
            }
            entity.setParty(organization);
            organization.getRoles().add(entity);

            return this;
        }
        
        public Builder module(String module) {
            entity.setModule(module);
            return this;
        }

    }

    public ApplicationSecurityGroup() {
    }

    public ApplicationSecurityGroup(Long id, Long orgId, String groupname, Boolean active) {
        super.setId(id);
        Organization organization = new Organization(orgId, groupname);
        setOrganization(organization);
//        this.module = module;
        this.active = active;
    }

//    @Column(unique = true)    
//    @Transient
//    private String groupname;

    @Column(name = "MODULE_NAME", length = 255, nullable = false)
    @NotNull
    private String module;

    @Lob
    private String remarks;

    @OneToMany(mappedBy = "securityGroup", cascade = CascadeType.ALL)
    private List<GroupAccessControl> accessControls;

    @OneToMany(mappedBy = "group", cascade = CascadeType.REMOVE)
    private List<ApplicationSecurityMap> users;

    @Transient
    private boolean active = false;

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

//    public String getGroupname() {
//        return groupname;
//    }

//    public void setGroupname(String groupname) {
//        this.groupname = groupname;
//    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public List<ApplicationSecurityMap> getUsers() {
        return users;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public void setUsers(List<ApplicationSecurityMap> users) {
        this.users = users;
    }

    public List<GroupAccessControl> getAccessControls() {
        return accessControls;
    }

    public void setAccessControls(List<GroupAccessControl> accessControls) {
        this.accessControls = accessControls;
    }

    public String getAccessControlList() {
        return getAccessControls().stream()
                .map(GroupAccessControl::getAccessControl)
                .map(AccessControl::getDescription)
                .collect(joining(", "));
    }
    
    final public void setOrganization(Organization organization) {
        setParty(organization);
    }
    
    final public Organization getOrganization() {
        return (Organization) getParty();
    }

}
