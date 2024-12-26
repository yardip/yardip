/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.party.entity;

import id.my.mdn.kupu.core.base.model.EntityBuilder;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 *
 * @author aphasan
 */
@Entity
@Table(name = "PARTY_PARTYCONTACTMECHANISM")
public class PartyContactMechanism implements Serializable {

    private static final long serialVersionUID = 1L;

    public static class Builder extends EntityBuilder<PartyContactMechanism> {

        public Builder() {
            super(new PartyContactMechanism());
            
            PartyContactMechanismId id = new PartyContactMechanismId();
            id.setFromDate(LocalDate.now());
            entity.setId(id);
            
            entity.setPurposes(new ArrayList<>());
        }
        
        public Builder party(Party party) {
            entity.setParty(party);
            return this;
        }
        
        public Builder contactMechanism(ContactMechanism contactMechanism) {
            entity.setContactMechanism(contactMechanism);
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
    private PartyContactMechanismId id;

    @MapsId("party")
    @ManyToOne
    private Party party;

    @MapsId("contactMechanism")
    @ManyToOne(cascade = CascadeType.ALL)
    private ContactMechanism contactMechanism;

    @Column(columnDefinition = "DATE")
    private LocalDate thruDate;

    @OneToMany(mappedBy = "partyContactMechanism", cascade = CascadeType.ALL)
    private List<PartyContactMechanismPurpose> purposes;

    private String remarks;

    private boolean defaulted;

    @PrePersist
    private void prePersist() {
        if (id.getFromDate() == null) {
            id.setFromDate(LocalDate.now());
        }
    }

    @Override
    public String toString() {
        return id != null ? id.toString() : null;
    }

    public PartyContactMechanismId getId() {
        return id;
    }

    public void setId(PartyContactMechanismId id) {
        this.id = id;
    }

    public Party getParty() {
        return party;
    }

    public void setParty(Party party) {
        this.party = party;
    }

    public ContactMechanism getContactMechanism() {
        return contactMechanism;
    }

    public void setContactMechanism(ContactMechanism contactMechanism) {
        this.contactMechanism = contactMechanism;
    }

    public LocalDate getThruDate() {
        return thruDate;
    }

    public void setThruDate(LocalDate thruDate) {
        this.thruDate = thruDate;
    }

    public List<PartyContactMechanismPurpose> getPurposes() {
        return purposes;
    }

    public void setPurposes(List<PartyContactMechanismPurpose> purposes) {
        this.purposes = purposes;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getPurpose() {
        return getPurposes().stream()
                .map(p -> p.getPurposeType().getRemarks())
                .collect(Collectors.joining(" / ", "", ""));
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
        hash = 29 * hash + Objects.hashCode(this.id);
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
        final PartyContactMechanism other = (PartyContactMechanism) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

}
