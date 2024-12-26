/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.party.entity;

import id.my.mdn.kupu.core.base.model.EntityBuilder;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import java.util.ArrayList;

/**
 *
 * @author aphasan
 */
@Entity
@Table(name = "PARTY_ORGANIZATION")
public class Organization extends Party {


    public static class Builder extends EntityBuilder<Organization> {

        public Builder() {
            super(new Organization());
//            entity.setType(Organization.class.getSimpleName());
            entity.setRoles(new ArrayList<>());
            entity.setContactMechanisms(new ArrayList<>());
            entity.setClassifications(new ArrayList<>());
        }

        public Builder postalAddress(PostalAddress postalAddress) {
            PartyContactMechanism contact = PartyContactMechanism.builder()
                    .party(entity)
                    .contactMechanism(postalAddress)
                    .defaulted(true)
                    .get();
            entity.getContactMechanisms().add(contact);
            return this;
        }

        public Builder telecommunicationNumber(TelecommunicationNumber telecommunicationNumber) {
            PartyContactMechanism contact = PartyContactMechanism.builder()
                    .party(entity)
                    .contactMechanism(telecommunicationNumber)
                    .defaulted(true)
                    .get();
            entity.getContactMechanisms().add(contact);
            return this;
        }

        public Builder electronicAddress(ElectronicAddress electronicAddress) {
            PartyContactMechanism contact = PartyContactMechanism.builder()
                    .party(entity)
                    .contactMechanism(electronicAddress)
                    .defaulted(true)
                    .get();
            entity.getContactMechanisms().add(contact);
            return this;
        }

        public Builder name(String name) {
            entity.setName(name);
            return this;
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    
    private String name;
    
    @Lob
    private byte [] logo;

    public Organization() {
    }

    public Organization(Long id, String name) {
        setId(id);
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getLogo() {
        return logo;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }
}
