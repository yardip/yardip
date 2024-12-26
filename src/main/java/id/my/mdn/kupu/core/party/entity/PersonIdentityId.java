/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.party.entity;

import id.my.mdn.kupu.core.base.util.EntityUtil;
import java.io.Serializable;
import java.util.Objects;
import jakarta.persistence.Embeddable;

/**
 *
 * @author aphasan
 */
@Embeddable
public class PersonIdentityId implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String  idType;
    
    private Long person;

    public PersonIdentityId() {
        
    }

    public PersonIdentityId(String idType, Long person) {
        this.idType = idType;
        this.person = person;
    }

    public PersonIdentityId(String... params) {
        this(params[0], Long.valueOf(params[1]));
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public Long getPerson() {
        return person;
    }

    public void setPerson(Long person) {
        this.person = person;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.idType);
        hash = 79 * hash + Objects.hashCode(this.person);
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
        final PersonIdentityId other = (PersonIdentityId) obj;
        if (!Objects.equals(this.idType, other.idType)) {
            return false;
        }
        if (!Objects.equals(this.person, other.person)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return EntityUtil.createStringId(idType, person);
    }
}
