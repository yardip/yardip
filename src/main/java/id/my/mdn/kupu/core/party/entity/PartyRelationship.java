/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.party.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;



/**
 *
 * @author aphasan
 */
@Entity
@Table(name = "PARTY_PARTYRELATIONSHIP")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "PARTYRELATIONSHIPTYPE_ID")
public abstract class PartyRelationship implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @EmbeddedId     
    private PartyRelationshipId id;
    
    @MapsId("fromRole")
    @ManyToOne     
    private PartyRole fromRole;
    
    @MapsId("toRole")
    @ManyToOne     
    private PartyRole toRole;
    
    @MapsId("partyRelationshipType")
    @ManyToOne
    private PartyRelationshipType partyRelationshipType;
    
    @Column   
    private LocalDate thruDate;
    
    @PrePersist
    private void prePersist() {
        if(getFromDate() == null) {
            setFromDate(LocalDate.now());
        }
    }

    public LocalDate getFromDate() {
        if(id == null) id = new PartyRelationshipId();
        return id.getFromDate();
    }

    public void setFromDate(LocalDate fromDate) {
        if(id == null) id = new PartyRelationshipId();
        id.setFromDate(fromDate);
    }

    @Override
    public String toString() {
        return id != null ? id.toString() : null;
    }

    public PartyRelationshipId getId() {
        return id;
    }

    public void setId(PartyRelationshipId id) {
        this.id = id;
    }

    public PartyRole getFromRole() {
        return fromRole;
    }

    public void setFromRole(PartyRole fromRole) {
        this.fromRole = fromRole;
    }

    public PartyRole getToRole() {
        return toRole;
    }

    public void setToRole(PartyRole toRole) {
        this.toRole = toRole;
    }

    public LocalDate getThruDate() {
        return thruDate;
    }

    public void setThruDate(LocalDate thruDate) {
        this.thruDate = thruDate;
    }

    public PartyRelationshipType getPartyRelationshipType() {
        return partyRelationshipType;
    }

    public void setPartyRelationshipType(PartyRelationshipType partyRelationshipType) {
        this.partyRelationshipType = partyRelationshipType;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.id);
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
        final PartyRelationship other = (PartyRelationship) obj;
        return Objects.equals(this.id, other.id);
    }
    
}
