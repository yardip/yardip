/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.party.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.TableGenerator;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;



/**
 *
 * @author aphasan
 */
@Entity
@Table(name = "PARTY_PARTYROLE")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "PARTYROLETYPE_ID")
public abstract class PartyRole implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @TableGenerator(name = "Party_PartyRole", table = "KEYGEN", allocationSize = 1)
    @GeneratedValue(generator = "Party_PartyRole", strategy = GenerationType.TABLE)     
    private Long id;
    
    @ManyToOne
    private Party party;
    
    @ManyToOne(fetch = FetchType.EAGER)
    private PartyRoleType partyRoleType;
    
    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime fromDate;
    
    @Column(columnDefinition = "TIMESTAMP")  
    private LocalDateTime thruDate;
    
    @OneToMany(mappedBy = "fromRole", cascade = CascadeType.ALL)
    private List<PartyRelationship> sourceRelationships;
    
    @OneToMany(mappedBy = "toRole", cascade = CascadeType.ALL)
    private List<PartyRelationship> targetRelationships;

    public PartyRole() {
    }

    public PartyRole(Long id, Party party, PartyRoleType roleType) {
        this.id = id;
        this.party = party;
        this.partyRoleType = roleType;
    }
    
    @PrePersist
    private void prePersist() {
        if(fromDate == null) {
            fromDate = LocalDateTime.now();
        }
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PartyRole)) {
            return false;
        }
        PartyRole other = (PartyRole) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return id != null ? String.valueOf(id) : null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Party getParty() {
        return party;
    }

    public void setParty(Party party) {
        this.party = party;
    }

    public PartyRoleType getPartyRoleType() {
        return partyRoleType;
    }

    public void setPartyRoleType(PartyRoleType partyRoleType) {
        this.partyRoleType = partyRoleType;
    }

    public LocalDateTime getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDateTime fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDateTime getThruDate() {
        return thruDate;
    }

    public void setThruDate(LocalDateTime thruDate) {
        this.thruDate = thruDate;
    }

    public List<PartyRelationship> getSourceRelationships() {
        return sourceRelationships;
    }

    public void setSourceRelationships(List<PartyRelationship> sourceRelationships) {
        this.sourceRelationships = sourceRelationships;
    }

    public List<PartyRelationship> getTargetRelationships() {
        return targetRelationships;
    }

    public void setTargetRelationships(List<PartyRelationship> targetRelationships) {
        this.targetRelationships = targetRelationships;
    }
    
}
