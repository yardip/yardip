/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.core.accounting.entity;

import id.my.mdn.kupu.core.base.util.Constants;
import id.my.mdn.kupu.core.base.util.EntityUtil;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Embeddable
public class BusinessEntityGLAccountId implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Long account;
    
    private Long entity;
    
    private LocalDate fromDate;

    public BusinessEntityGLAccountId() {}

    public BusinessEntityGLAccountId(Long account, Long entity, LocalDate fromDate) {
        this.account = account;
        this.entity = entity;
        this.fromDate = fromDate;
    }

    public BusinessEntityGLAccountId(String... params) {
        this(Long.valueOf(params[0]), Long.valueOf(params[1]), 
                EntityUtil.stringKeyToLocalDate(params[2], Constants.KEYFORMAT_LOCALDATE));
    }

    public Long getAccount() {
        return account;
    }

    public void setAccount(Long account) {
        this.account = account;
    }

    public Long getEntity() {
        return entity;
    }

    public void setEntity(Long entity) {
        this.entity = entity;
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
        hash = 83 * hash + Objects.hashCode(this.account);
        hash = 83 * hash + Objects.hashCode(this.entity);
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
        final BusinessEntityGLAccountId other = (BusinessEntityGLAccountId) obj;
        if (!Objects.equals(this.account, other.account)) {
            return false;
        }
        return Objects.equals(this.entity, other.entity);
    }

    @Override
    public String toString() {
        return EntityUtil.createStringId(account, entity, 
                EntityUtil.stringKeyFromLocalDate(fromDate, Constants.KEYFORMAT_LOCALDATE));
    }
    
}
