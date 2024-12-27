/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.core.accounting.entity;

import id.my.mdn.kupu.core.party.entity.BusinessEntity;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Entity
@Table(name = "ACCOUNTING_BUSINESSENTITYGLACCOUNT")
public class BusinessEntityGLAccount implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @EmbeddedId
    private BusinessEntityGLAccountId id;
    
    @MapsId("account")
    @ManyToOne
    private GLAccount account;
    
    @MapsId("entity")
    @ManyToOne
    private BusinessEntity entity;
    
    private LocalDate thruDate;

    public BusinessEntityGLAccountId getId() {
        return id;
    }

    public void setId(BusinessEntityGLAccountId id) {
        this.id = id;
    }

    public GLAccount getAccount() {
        return account;
    }

    public void setAccount(GLAccount account) {
        this.account = account;
    }

    public BusinessEntity getEntity() {
        return entity;
    }

    public void setEntity(BusinessEntity entity) {
        this.entity = entity;
    }

    public LocalDate getFromDate() {
        return id.getFromDate();
    }

    public void setFromDate(LocalDate fromDate) {
        id.setFromDate(fromDate);
    }

    public LocalDate getThruDate() {
        return thruDate;
    }

    public void setThruDate(LocalDate thruDate) {
        this.thruDate = thruDate;
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
        if (!(object instanceof BusinessEntityGLAccount)) {
            return false;
        }
        BusinessEntityGLAccount other = (BusinessEntityGLAccount) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "id.my.mdn.kupu.app.accounting.entity.GeneralLedgerRoleAccount[ id=" + id + " ]";
    }
    
}
