/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.party.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
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
@Table(name = "PARTY_CONTACTMECHANISMPURPOSE")
public class PartyContactMechanismPurpose implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private PartyContactMechanismPurposeId id;

    @MapsId("partyContactMechanism")
    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "PARTYCONTACTMECHANISM_PARTY_ID", referencedColumnName = "PARTY_ID"),
        @JoinColumn(name = "PARTYCONTACTMECHANISM_CONTACTMECHANISM_ID", referencedColumnName = "CONTACTMECHANISM_ID"),
        @JoinColumn(name = "PARTYCONTACTMECHANISM_FROMDATE", referencedColumnName = "FROMDATE")
    })
    private PartyContactMechanism partyContactMechanism;

    @MapsId("purposeType")
    @ManyToOne
    private ContactMechanismPurposeType purposeType;

    @Column(columnDefinition = "DATE")
    private LocalDate thruDate;    
    
    @PrePersist
    private void prePersist() {
        if(id.getFromDate() == null) {
            id.setFromDate(LocalDate.now());
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.id);
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
        final PartyContactMechanismPurpose other = (PartyContactMechanismPurpose) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    public PartyContactMechanismPurposeId getId() {
        return id;
    }

    public void setId(PartyContactMechanismPurposeId id) {
        this.id = id;
    }

    public PartyContactMechanism getPartyContactMechanism() {
        return partyContactMechanism;
    }

    public void setPartyContactMechanism(PartyContactMechanism partyContactMechanism) {
        this.partyContactMechanism = partyContactMechanism;
    }

    public ContactMechanismPurposeType getPurposeType() {
        return purposeType;
    }

    public void setPurposeType(ContactMechanismPurposeType purposeType) {
        this.purposeType = purposeType;
    }

    public LocalDate getThruDate() {
        return thruDate;
    }

    public void setThruDate(LocalDate thruDate) {
        this.thruDate = thruDate;
    }

}
