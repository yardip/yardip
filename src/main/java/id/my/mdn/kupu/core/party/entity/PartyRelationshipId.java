/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.party.entity;

import id.my.mdn.kupu.core.base.util.Constants;
import id.my.mdn.kupu.core.base.util.EntityUtil;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 *
 * @author aphasan
 */
@Embeddable

public class PartyRelationshipId implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "FROMROLE_ID")  
    private Long fromRole;

    @Column(name = "TOROLE_ID")     
    private Long toRole;

    @Column     
    private LocalDate fromDate;
    
    @Column(name = "PARTYRELATIONSHIPTYPE_ID")     
    private String partyRelationshipType;

    public PartyRelationshipId() {
    }

    public PartyRelationshipId(Long fromRole, Long toRole, LocalDate fromDate, String partyRelationshipType) {
        this.fromRole = fromRole;
        this.toRole = toRole;
        this.fromDate = fromDate;
        this.partyRelationshipType = partyRelationshipType;
    }

    public PartyRelationshipId(String... params) {
        this(
                Long.valueOf(params[0]),
                Long.valueOf(params[1]),
                EntityUtil.stringKeyToLocalDate(params[2], Constants.KEYFORMAT_LOCALDATE), 
                params[3]
        );
    }

    @Override
    public String toString() {
        return EntityUtil.createStringId(
                fromRole, 
                toRole,
                EntityUtil.stringKeyFromLocalDate(fromDate, Constants.KEYFORMAT_LOCALDATE),
                partyRelationshipType
        );
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.fromRole);
        hash = 59 * hash + Objects.hashCode(this.toRole);
        hash = 59 * hash + Objects.hashCode(this.fromDate);
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
        final PartyRelationshipId other = (PartyRelationshipId) obj;
        if (!Objects.equals(this.fromRole, other.fromRole)) {
            return false;
        }
        if (!Objects.equals(this.toRole, other.toRole)) {
            return false;
        }
        return Objects.equals(this.fromDate, other.fromDate);
    }

    public Long getFromRole() {
        return fromRole;
    }

    public void setFromRole(Long fromRole) {
        this.fromRole = fromRole;
    }

    public Long getToRole() {
        return toRole;
    }

    public void setToRole(Long toRole) {
        this.toRole = toRole;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public String getPartyRelationshipType() {
        return partyRelationshipType;
    }

    public void setPartyRelationshipType(String partyRelationshipType) {
        this.partyRelationshipType = partyRelationshipType;
    }

}
