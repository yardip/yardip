/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.core.party.entity;

import id.my.mdn.kupu.core.base.model.EntityBuilder;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.ArrayList;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Entity
@Table(name = "PARTY_SUBSIDIARYORGANIZATION")
public class SubsidiaryOrganization extends OrganizationRole {
    
    public static final Builder builder() {
        return new Builder();
    }

    public static final class Builder extends EntityBuilder<SubsidiaryOrganization> {

        public Builder() {
            super(new SubsidiaryOrganization());
        }

        public Builder organization(Organization organization) {
            if (organization.getRoles() == null) {
                organization.setRoles(new ArrayList<>());
            }
            entity.setParty(organization);
            organization.getRoles().add(entity);

            return this;
        }
        
        public Builder parent(ParentOrganization parentOrganization) {
            entity.setParentOrganization(parentOrganization);
            parentOrganization.getSubsidiaries().add(entity);
            
            return this;
        }

    }
    
    @ManyToOne
    private ParentOrganization parentOrganization;

    public ParentOrganization getParentOrganization() {
        return parentOrganization;
    }

    public void setParentOrganization(ParentOrganization parentOrganization) {
        this.parentOrganization = parentOrganization;
    }
}
