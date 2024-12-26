/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.party.entity;

import id.my.mdn.kupu.core.base.model.EntityBuilder;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author aphasan
 */
@Entity
@Table(name = "PARTY_PERSONIDENTITY")
public class PersonIdentity implements Serializable {

    private static final long serialVersionUID = 1L;

    public static class Builder extends EntityBuilder<PersonIdentity> {

        public Builder() {
            super(new PersonIdentity());
        }
        
        public Builder person(Person person) {
            entity.setPerson(person);
            return this;
        }
        
        public Builder defaulted(boolean defaulted) {
            entity.setDefaulted(defaulted);
            return this;
        }
    }

    public static Builder builder() {
        return new Builder();
    }
    
    @EmbeddedId
    private PersonIdentityId id;
    
    @MapsId("idType")
    @ManyToOne
    private PersonIdentityType idType; 
    
    @MapsId("person")
    @ManyToOne
    private Person person;
    
    private String code;
    
    private boolean defaulted;

    public PersonIdentity() {
    }

//    public PersonIdentity(Person person) {
//        this.person = person;
//    }

    public PersonIdentityId getId() {
        return id;
    }

    public void setId(PersonIdentityId id) {
        this.id = id;
    }

    public PersonIdentityType getIdType() {
        return idType;
    }

    public void setIdType(PersonIdentityType idType) {
        this.idType = idType;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isDefaulted() {
        return defaulted;
    }

    public void setDefaulted(boolean defaulted) {
        this.defaulted = defaulted;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 19 * hash + Objects.hashCode(this.id);
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
        final PersonIdentity other = (PersonIdentity) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return id != null ? id.toString() : null;
    }
    
}
