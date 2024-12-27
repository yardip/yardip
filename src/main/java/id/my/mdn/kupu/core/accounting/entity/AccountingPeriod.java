/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.core.accounting.entity;

import id.my.mdn.kupu.core.base.model.HierarchicalEntity;
import id.my.mdn.kupu.core.party.entity.BusinessEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Entity
@Table(name = "ACCOUNTING_ACCOUNTINGPERIOD")
public class AccountingPeriod implements Serializable, HierarchicalEntity<AccountingPeriod> {

    private static final long serialVersionUID = 1L;
    
    @EmbeddedId
    private AccountingPeriodId id;
    
    private String name;
    
    @MapsId("businessEntity")
    @ManyToOne
    private BusinessEntity businessEntity;
    
    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "PARENT_BUSINESSENTITY_ID", referencedColumnName = "BUSINESSENTITY_ID"),
        @JoinColumn(name = "PARENT_FROMDATE", referencedColumnName = "FROMDATE"),
        @JoinColumn(name = "PARENT_THRUDATE", referencedColumnName = "THRUDATE")
    })
    private AccountingPeriod parent;
    
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<AccountingPeriod> children;
    
    private String flag;

    public AccountingPeriodId getId() {
        return id;
    }

    public void setId(AccountingPeriodId id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BusinessEntity getBusinessEntity() {
        return businessEntity;
    }

    public void setBusinessEntity(BusinessEntity businessEntity) {
        this.businessEntity = businessEntity;
    }

    public LocalDate getFromDate() {
        return id.getFromDate();
    }

    public void setFromDate(LocalDate fromDate) {
        id.setFromDate(fromDate);
    }

    public LocalDate getThruDate() {
        return id.getThruDate();
    }

    public void setThruDate(LocalDate thruDate) {
        id.setThruDate(thruDate);
    }

    @Override
    public AccountingPeriod getParent() {
        return parent;
    }

    @Override
    public void setParent(AccountingPeriod parent) {
        this.parent = parent;
    }

    @Override
    public List<AccountingPeriod> getChildren() {
        return children;
    }

    @Override
    public void setChildren(List<AccountingPeriod> children) {
        this.children = children;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
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
        if (!(object instanceof AccountingPeriod)) {
            return false;
        }
        AccountingPeriod other = (AccountingPeriod) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }
    
}
