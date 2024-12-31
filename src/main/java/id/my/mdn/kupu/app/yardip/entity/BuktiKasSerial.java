/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.yardip.entity;

import id.my.mdn.kupu.core.accounting.entity.AccountingPeriod;
import id.my.mdn.kupu.core.party.entity.BusinessEntity;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Entity
@Table(name = "YARDIP_BUKTIKASSERIAL")
public class BuktiKasSerial implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @EmbeddedId
    private BuktiKasSerialId id;
    
    @MapsId("period")
    @ManyToOne
    private AccountingPeriod period;
    
    @MapsId("entity")
    @ManyToOne
    private BusinessEntity entity;
    
    private int seed;

    public BuktiKasSerial() {
    }

    public BuktiKasSerial(AccountingPeriod period, BusinessEntity entity) {
        this.id = new BuktiKasSerialId(period.getId(), entity.getId());
        this.period = period;
        this.entity = entity;
        this.seed = 1;
    }

    @Override
    public String toString() {
        return id != null ? String.valueOf(id) : null;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.id);
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
        final BuktiKasSerial other = (BuktiKasSerial) obj;
        return Objects.equals(this.id, other.id);
    }

    public BuktiKasSerialId getId() {
        return id;
    }

    public void setId(BuktiKasSerialId id) {
        this.id = id;
    }

    public AccountingPeriod getPeriod() {
        return period;
    }

    public void setPeriod(AccountingPeriod period) {
        this.period = period;
    }

    public BusinessEntity getEntity() {
        return entity;
    }

    public void setEntity(BusinessEntity entity) {
        this.entity = entity;
    }

    public int getSeed() {
        return seed;
    }

    public void setSeed(int seed) {
        this.seed = seed;
    }
    
}
