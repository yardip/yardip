/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.party.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.TableGenerator;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import static java.util.stream.Collectors.joining;

/**
 *
 * @author aphasan
 */
@Entity
@Table(name = "PARTY_PARTY")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Party implements Serializable {

    @Id
    @TableGenerator(name = "Party_Party", table = "KEYGEN", allocationSize = 1)
    @GeneratedValue(generator = "Party_Party", strategy = GenerationType.TABLE)
    private Long id;

    @Column(name = "dtype")
    private String type;

    @OneToMany(mappedBy = "party", cascade = CascadeType.ALL)
    private List<PartyClassification> classifications;

    @OneToMany(mappedBy = "party", cascade = CascadeType.ALL)
    private List<PartyContactMechanism> contactMechanisms;

    @OneToMany(mappedBy = "party", cascade = CascadeType.ALL)
    private List<PartyRole> roles;

    public String getPartyRoles() {
        List<PartyRole> result = getRoles();
        return result.stream()
                .map(role -> role.getPartyRoleType().getName())
                .collect(joining(", "));
    }

    public String getPartyClassifications() {
        List<PartyClassification> result = getClassifications();
        return result.stream()
                .map(role -> role.getPartyType().getName())
                .collect(joining(", "));
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 43 * hash + Objects.hashCode(this.id);
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
        final Party other = (Party) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return id != null ? id.toString() : null;
    }

    public List<PartyClassification> getClassifications() {
        return classifications;
    }

    public void setClassifications(List<PartyClassification> classifications) {
        this.classifications = classifications;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<PartyContactMechanism> getContactMechanisms() {
        return contactMechanisms;
    }

    public void setContactMechanisms(List<PartyContactMechanism> contactMechanisms) {
        this.contactMechanisms = contactMechanisms;
    }

    public List<PartyRole> getRoles() {
        return roles;
    }

    public void setRoles(List<PartyRole> roles) {
        this.roles = roles;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public abstract String getName();

    public <T extends Party> T as(Class<T> partyClass) {
        return partyClass.cast(this);
    }
    
    public PartyContactMechanism getPostalAddress() {
        if(contactMechanisms != null) {
            for(PartyContactMechanism partyContactMechanism : contactMechanisms) {
                if(partyContactMechanism.getContactMechanism() instanceof PostalAddress && partyContactMechanism.isDefaulted()) {
                    return partyContactMechanism;
                }
            }
        } 
        return null;
    }
    
    public PartyContactMechanism getTelecommunicationNumber() {
        if(contactMechanisms != null) {
            for(PartyContactMechanism partyContactMechanism : contactMechanisms) {
                if(partyContactMechanism.getContactMechanism() instanceof TelecommunicationNumber && partyContactMechanism.isDefaulted()) {
                    return partyContactMechanism;
                }
            }
        } 
        return null;
    }
    
    public PartyContactMechanism getElectronicAddress() {
        if(contactMechanisms != null) {
            for(PartyContactMechanism partyContactMechanism : contactMechanisms) {
                if(partyContactMechanism.getContactMechanism() instanceof ElectronicAddress && partyContactMechanism.isDefaulted()) {
                    return partyContactMechanism;
                }
            }
        } 
        return null;
    }
}
