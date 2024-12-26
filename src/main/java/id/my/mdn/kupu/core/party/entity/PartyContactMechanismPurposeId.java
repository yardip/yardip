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
public class PartyContactMechanismPurposeId implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private PartyContactMechanismId partyContactMechanism;    
    
    private String purposeType;
    
    @Column(name="STARTDATE", columnDefinition = "DATE")
    private LocalDate fromDate;

    public PartyContactMechanismPurposeId() {
    }

    public PartyContactMechanismPurposeId(String purposeType, PartyContactMechanismId partyContactMechanism, LocalDate fromDate) {
        this.purposeType = purposeType;
        this.partyContactMechanism = partyContactMechanism;
        this.fromDate = fromDate;
    }

    public PartyContactMechanismPurposeId(String... params) {
        this(params[0], new PartyContactMechanismId(new String[]{params[1], params[2], params[3]}), 
                EntityUtil.stringKeyToLocalDate(params[4], Constants.FORMAT_LOCALDATE));
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + Objects.hashCode(this.partyContactMechanism);
        hash = 83 * hash + Objects.hashCode(this.purposeType);
        hash = 83 * hash + Objects.hashCode(this.fromDate);
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
        final PartyContactMechanismPurposeId other = (PartyContactMechanismPurposeId) obj;
        if (!Objects.equals(this.purposeType, other.purposeType)) {
            return false;
        }
        if (!Objects.equals(this.partyContactMechanism, other.partyContactMechanism)) {
            return false;
        }
        if (!Objects.equals(this.fromDate, other.fromDate)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return EntityUtil.createStringId(purposeType, EntityUtil.stringKeyFromLocalDate(fromDate, Constants.KEYFORMAT_LOCALDATE));
    }

    public PartyContactMechanismId getPartyContactMechanism() {
        return partyContactMechanism;
    }

    public void setPartyContactMechanism(PartyContactMechanismId partyContactMechanism) {
        this.partyContactMechanism = partyContactMechanism;
    }

    public String getPurposeType() {
        return purposeType;
    }

    public void setPurposeType(String purposeType) {
        this.purposeType = purposeType;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

}
