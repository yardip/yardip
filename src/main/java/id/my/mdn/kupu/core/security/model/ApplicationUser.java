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
import id.my.mdn.kupu.core.party.entity.Party;
import id.my.mdn.kupu.core.party.entity.PersonRole;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import static java.util.stream.Collectors.joining;

/**
 *
 * @author Arief Prihasanto <ariefp5758 at gmail.com>
 */
@Entity
@Table(name = "SECURITY_APPLICATIONUSER")
public class ApplicationUser extends PersonRole {

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends EntityBuilder<ApplicationUser> {

        public Builder() {
            super(new ApplicationUser());
        }

        public Builder withParty(Party party) {
            if (party.getRoles() == null) {
                party.setRoles(new ArrayList<>());
            }
            entity.setParty(party);
            party.getRoles().add(entity);

            return this;
        }
        
        public Builder withUsername(String username) {
            entity.setUsername(username);
            return this;
        }
        
        public Builder withPassword(String password) {
            entity.setPassword(password);            
            return this;
        }
        
        public Builder addToGroup(ApplicationSecurityGroup group) {
            if(entity.getGroups() == null) {
                entity.setGroups(new ArrayList<>());
            }
            ApplicationSecurityMap securityMap = new ApplicationSecurityMap();
            securityMap.setUser(entity);
            securityMap.setGroup(group);
            
            entity.getGroups().add(securityMap);
            
            return this;
        }

    }

    @Column(unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<ApplicationSecurityMap> groups;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<ApplicationSecurityMap> getGroups() {
        return groups;
    }

    public void setGroups(List<ApplicationSecurityMap> groups) {
        this.groups = groups;
    }

    public String getGroupList() {
        return getGroups().stream()
                .map(ApplicationSecurityMap::getGroup)
                .map(group -> group.getOrganization().getName())
                .collect(joining(", "));
    }

}
