/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.yardip.model;

import id.my.mdn.kupu.core.accounting.entity.AccountingPeriodId;
import id.my.mdn.kupu.core.base.util.EntityUtil;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Embeddable
public class BuktiKasSerialId implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private AccountingPeriodId period;
    
    private Long entity;

    public BuktiKasSerialId() {
    }

    public BuktiKasSerialId(AccountingPeriodId period, Long entity) {
        this.period = period;
        this.entity = entity;
    }

    public BuktiKasSerialId(String... params) {
        this(new AccountingPeriodId(params), Long.valueOf(params[2]));
    }

    @Override
    public String toString() {        
        return EntityUtil.createStringId(period, entity);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 13 * hash + Objects.hashCode(this.period);
        hash = 13 * hash + Objects.hashCode(this.entity);
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
        final BuktiKasSerialId other = (BuktiKasSerialId) obj;
        if (!Objects.equals(this.period, other.period)) {
            return false;
        }
        return Objects.equals(this.entity, other.entity);
    }

    public AccountingPeriodId getPeriod() {
        return period;
    }

    public void setPeriod(AccountingPeriodId period) {
        this.period = period;
    }

    public Long getEntity() {
        return entity;
    }

    public void setEntity(Long entity) {
        this.entity = entity;
    }
    
}
