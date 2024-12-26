/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.party.entity;

import id.my.mdn.kupu.core.base.util.Constants;
import id.my.mdn.kupu.core.base.util.EntityUtil;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

/**
 *
 * @author Medina Computama <medina.computama@gmail.com>
 */
@Embeddable
public class PartyClassificationId implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Long party;
    
    private Long partyType;
    
    @Column(columnDefinition = "DATE")
    private LocalDate fromDate;

    public PartyClassificationId() {
    }

    public PartyClassificationId(Long party, Long partyType, LocalDate fromDate) {
        this.party = party;
        this.partyType = partyType;
        this.fromDate = fromDate;
    }

    public PartyClassificationId(String... params) {
        this(Long.parseLong(params[0]), Long.parseLong(params[1]), 
                EntityUtil.stringKeyToLocalDate(params[2], Constants.FORMAT_LOCALDATE));
    }

    public Long getParty() {
        return party;
    }

    public void setParty(Long party) {
        this.party = party;
    }

    public Long getPartyType() {
        return partyType;
    }

    public void setPartyType(Long partyType) {
        this.partyType = partyType;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.party);
        hash = 47 * hash + Objects.hashCode(this.partyType);
        hash = 47 * hash + Objects.hashCode(this.fromDate);
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
        final PartyClassificationId other = (PartyClassificationId) obj;
        if (!Objects.equals(this.party, other.party)) {
            return false;
        }
        if (!Objects.equals(this.partyType, other.partyType)) {
            return false;
        }
        if (!Objects.equals(this.fromDate, other.fromDate)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return EntityUtil.createStringId(party, partyType, EntityUtil.stringKeyFromLocalDate(fromDate, Constants.KEYFORMAT_LOCALDATE));
    }
    
}
