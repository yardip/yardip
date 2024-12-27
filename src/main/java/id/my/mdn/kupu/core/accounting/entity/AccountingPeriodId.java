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
public class AccountingPeriodId implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long businessEntity;

    private LocalDate fromDate;

    private LocalDate thruDate;

    public AccountingPeriodId() {
    }

    public AccountingPeriodId(Long businessEntity, LocalDate fromDate, LocalDate thruDate) {
        this.businessEntity = businessEntity;
        this.fromDate = fromDate;
        this.thruDate = thruDate;
    }

    public AccountingPeriodId(String... params) {
        this(
                Long.valueOf(params[0]),
                EntityUtil.stringKeyToLocalDate(params[1], Constants.KEYFORMAT_LOCALDATE),
                EntityUtil.stringKeyToLocalDate(params[2], Constants.KEYFORMAT_LOCALDATE)
        );
    }

    public Long getBusinessEntity() {
        return businessEntity;
    }

    public void setBusinessEntity(Long businessEntity) {
        this.businessEntity = businessEntity;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getThruDate() {
        return thruDate;
    }

    public void setThruDate(LocalDate thruDate) {
        this.thruDate = thruDate;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + Objects.hashCode(this.fromDate);
        hash = 59 * hash + Objects.hashCode(this.thruDate);
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
        final AccountingPeriodId other = (AccountingPeriodId) obj;
        if (!Objects.equals(this.fromDate, other.fromDate)) {
            return false;
        }
        return Objects.equals(this.thruDate, other.thruDate);
    }

    @Override
    public String toString() {
        return EntityUtil.createStringId(
                businessEntity,
                EntityUtil.stringKeyFromLocalDate(
                        fromDate, Constants.KEYFORMAT_LOCALDATE),
                EntityUtil.stringKeyFromLocalDate(
                        thruDate, Constants.KEYFORMAT_LOCALDATE)
        );
    }
}
