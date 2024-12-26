/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.party.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;

/**
 *
 * @author aphasan
 */
@Embeddable
public class Age implements Serializable {
    
    @Column(name = "AGE_YEARS")
    private Long years;
    
    @Column(name = "AGE_MONTHS")
    private Long months;
    
    @Column(name = "AGE_DAYS")
    private Long days;

    public Age() {
    }

    public Age(Long years, Long months, Long days) {
        this.years = years;
        this.months = months;
        this.days = days;
    }

    public Long getYears() {
        return years;
    }

    public void setYears(Long years) {
        this.years = years;
    }

    public Long getMonths() {
        return months;
    }

    public void setMonths(Long months) {
        this.months = months;
    }

    public Long getDays() {
        return days;
    }

    public void setDays(Long days) {
        this.days = days;
    }
}
