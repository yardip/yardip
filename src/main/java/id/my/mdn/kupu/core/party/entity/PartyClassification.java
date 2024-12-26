/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.party.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 *
 * @author Medina Computama <medina.computama@gmail.com>
 */
@Entity
@Table(name = "PARTY_PARTYCLASSIFICATION")
@NamedQueries({
    @NamedQuery(name = "PartyClassification.getExisted",
            query = "SELECT COUNT(pc) FROM PartyClassification pc "
            + "WHERE pc.party = :party "
            + "AND pc.partyType = :type "
            + "AND pc.id.fromDate <= :fromDate "
            + "AND ("
            + "pc.thruDate IS NULL OR pc.thruDate > :thruDate"
            + ")")
})
public class PartyClassification implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private PartyClassificationId id;

    @MapsId("party")
    @ManyToOne
    private Party party;

    @MapsId("partyType")
    @ManyToOne
    private PartyType partyType;

    @Column(columnDefinition = "DATE")
    private LocalDate thruDate;

    public PartyClassificationId getId() {
        return id;
    }

    public void setId(PartyClassificationId id) {
        this.id = id;
    }

    public Party getParty() {
        return party;
    }

    public void setParty(Party party) {
        this.party = party;
    }

    public PartyType getPartyType() {
        return partyType;
    }

    public void setPartyType(PartyType partyType) {
        this.partyType = partyType;
    }

    public LocalDate getThruDate() {
        return thruDate;
    }

    public void setThruDate(LocalDate thruDate) {
        this.thruDate = thruDate;
    }

    @PrePersist
    private void prePersists() {
        if (id.getFromDate() == null) {
            id.setFromDate(LocalDate.now());
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + Objects.hashCode(this.id);
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
        final PartyClassification other = (PartyClassification) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return id != null ? String.valueOf(id) : null;
    }

}
