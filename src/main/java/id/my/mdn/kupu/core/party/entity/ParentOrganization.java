/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.core.party.entity;

import id.my.mdn.kupu.core.base.model.EntityBuilder;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Entity
@Table(name = "PARTY_PARENTORGANIZATION")
public class ParentOrganization extends OrganizationRole {
    
    public static final Builder builder() {
        return new Builder();
    }

    public static final class Builder extends EntityBuilder<ParentOrganization> {

        public Builder() {
            super(new ParentOrganization());
        }

        public Builder organization(Organization organization) {
            if (organization.getRoles() == null) {
                organization.setRoles(new ArrayList<>());
            }
            entity.setParty(organization);
            organization.getRoles().add(entity);

            return this;
        }

    }
    
    @OneToMany(mappedBy = "parentOrganization")
    private List<SubsidiaryOrganization> subsidiaries;

    public List<SubsidiaryOrganization> getSubsidiaries() {
        return subsidiaries;
    }

    public void setSubsidiaries(List<SubsidiaryOrganization> subsidiaries) {
        this.subsidiaries = subsidiaries;
    }
}
