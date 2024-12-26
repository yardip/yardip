/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.party.entity;

import id.my.mdn.kupu.core.base.model.EntityBuilder;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author aphasan
 */
@Entity
@Table(name = "PARTY_PERSON")
public class Person extends Party {

    public static class Builder extends EntityBuilder<Person> {

        public Builder() {
            super(new Person());
//            entity.setType(Person.class.getSimpleName());
            entity.setRoles(new ArrayList<>());
            entity.setIdentities(new ArrayList<>());
            entity.setContactMechanisms(new ArrayList<>());
            entity.setClassifications(new ArrayList<>());
        }

        public Builder firstName(String firstName) {
            entity.setFirstName(firstName);
            return this;
        }

        public Builder lastName(String lastName) {
            entity.setLastName(lastName);
            return this;
        }
        
        public Builder dateOfBirth(LocalDate dob) {
            entity.setDateOfBirth(dob);
            return this;
        }
        
        public Builder identity(PersonIdentity identity) {
            identity.setPerson(entity);
            identity.setDefaulted(true);
            entity.getIdentities().add(identity);
            return this;
        }
        
        public Builder identity(Person person) {
            PersonIdentity identity = PersonIdentity.builder()
            .person(entity)
            .defaulted(true).get();
            entity.getIdentities().add(identity);
            return this;
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

        public Builder gender(GenderType genderType) {
            entity.setGender(genderType);
            return this;
        }

        public Builder maritalStatus(MaritalStatusType maritalStatusType) {
            entity.setMaritalStatus(maritalStatusType);
            return this;
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    @Column(nullable = false)
    private String firstName;

    private String lastName;
    
    @Column(columnDefinition = "DATE")
    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    private GenderType gender;

    @Enumerated(EnumType.STRING)
    private MaritalStatusType maritalStatus;    
    
    private String prefixTitle;
    
    private String suffixTitle;    
    
    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
    private List<PersonIdentity> identities;

    public Person() {
    }

    public Person(Long partyId, String firstName, String lastName, GenderType gender, LocalDate dateOfBirth) {
        setId(partyId);
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;        
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public GenderType getGender() {
        return gender;
    }

    public void setGender(GenderType gender) {
        this.gender = gender;
    }

    public MaritalStatusType getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(MaritalStatusType maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public List<PersonIdentity> getIdentities() {
        return identities;
    }

    public void setIdentities(List<PersonIdentity> identities) {
        this.identities = identities;
    }

    public String getPrefixTitle() {
        return prefixTitle;
    }

    public void setPrefixTitle(String prefixTitle) {
        this.prefixTitle = prefixTitle;
    }

    public String getSuffixTitle() {
        return suffixTitle;
    }

    public void setSuffixTitle(String suffixTitle) {
        this.suffixTitle = suffixTitle;
    }

    @Override
    public String getName() {
        return ((prefixTitle != null && !prefixTitle.isBlank()) ? (prefixTitle + ".") : "") 
                + ((firstName != null && !firstName.isBlank()) ? " " + firstName : "") 
                + ((lastName != null && !lastName.isBlank()) ? " " + lastName : "") 
                + ((suffixTitle != null && !suffixTitle.isBlank()) ? ", " + suffixTitle + "." : "");
    }
    
    public Age getAge() {
        if(dateOfBirth == null) return new Age(null, null, null);
        LocalDate now = LocalDate.now();
        Period age = dateOfBirth.until(now);
        return new Age(age.get(ChronoUnit.YEARS), age.get(ChronoUnit.MONTHS), age.get(ChronoUnit.DAYS));
    }
    
    public PersonIdentity getIdentity() {
        if(identities != null) {
            for(PersonIdentity identity : identities) {
                if(identity.isDefaulted()) {
                    return identity;
                }
            }
        } 
        return null;
    }
}
