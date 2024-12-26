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
 * @author aphasan
 */
@Embeddable
public class PartyContactMechanismId implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long party;

    private Long contactMechanism;

    @Column(columnDefinition = "DATE")
    private LocalDate fromDate;

    public PartyContactMechanismId() {}

    public PartyContactMechanismId(Long party, Long contactMechanism, LocalDate fromDate) {
        this.party = party;
        this.contactMechanism = contactMechanism;
        this.fromDate = fromDate;
    }

    public PartyContactMechanismId(String... params) {
        this(Long.valueOf(params[0]), Long.valueOf(params[1]),
                EntityUtil.stringKeyToLocalDate(params[2], Constants.KEYFORMAT_LOCALDATE));
    }

    public Long getParty() {
        return party;
    }

    public void setParty(Long party) {
        this.party = party;
    }

    public Long getContactMechanism() {
        return contactMechanism;
    }

    public void setContactMechanism(Long contactMechanism) {
        this.contactMechanism = contactMechanism;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    @Override
    public String toString() {
        return EntityUtil.createStringId(party, contactMechanism, EntityUtil.stringKeyFromLocalDate(fromDate, Constants.KEYFORMAT_LOCALDATE));
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 61 * hash + Objects.hashCode(this.party);
        hash = 61 * hash + Objects.hashCode(this.contactMechanism);
        hash = 61 * hash + Objects.hashCode(this.fromDate);
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
        final PartyContactMechanismId other = (PartyContactMechanismId) obj;
        if (!Objects.equals(this.party, other.party)) {
            return false;
        }
        if (!Objects.equals(this.contactMechanism, other.contactMechanism)) {
            return false;
        }
        if (!Objects.equals(this.fromDate, other.fromDate)) {
            return false;
        }
        return true;
    }

}
