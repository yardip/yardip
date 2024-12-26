/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.core.party.entity;

import id.my.mdn.kupu.core.base.model.EntityBuilder;
import id.my.mdn.kupu.core.base.model.HierarchicalEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Entity
@Table(name = "PARTY_BUSINESSENTITY")
public class BusinessEntity extends PartyRole implements HierarchicalEntity<BusinessEntity> {

    public static final Builder builder() {
        return new Builder();
    }

    public static final class Builder extends EntityBuilder<BusinessEntity> {

        public Builder() {
            super(new BusinessEntity());
        }

        public Builder organization(Organization organization) {
            if (organization.getRoles() == null) {
                organization.setRoles(new ArrayList<>());
            }
            entity.setParty(organization);
            organization.getRoles().add(entity);

            return this;
        }

        public Builder parent(BusinessEntity parent) {
            if (parent != null) {
                entity.setParent(parent);
                parent.getChildren().add(entity);
            }

            return this;
        }

    }

    @ManyToOne
    private BusinessEntity parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<BusinessEntity> children;
    
    @Transient
    private PostalAddress postalAddress;

    public void setPostalAddress(PostalAddress postalAddress) {
        this.postalAddress = postalAddress;
    }

    public PostalAddress getPostalAddress() {
        return postalAddress;
    }

    @Override
    public BusinessEntity getParent() {
        return parent;
    }

    @Override
    public void setParent(BusinessEntity parent) {
        this.parent = parent;
    }

    @Override
    public List<BusinessEntity> getChildren() {
        return children;
    }

    @Override
    public void setChildren(List<BusinessEntity> children) {
        this.children = children;
    }

    final public void setOrganization(Organization organization) {
        setParty(organization);
    }

    final public Organization getOrganization() {
        return (Organization) getParty();
    }

}
