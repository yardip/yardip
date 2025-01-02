/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.yardip.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author aphasan
 */
public class RekapitulasiLaba implements Serializable{
    
    private final String period;
    
    private final BigDecimal totalIncome;
    
    private final BigDecimal totalExpense;

    public RekapitulasiLaba(String period, BigDecimal totalIncome, BigDecimal totalExpense) {
        this.period = period;
        this.totalIncome = totalIncome;
        this.totalExpense = totalExpense;
    }

    public String getPeriod() {
        return period;
    }

    public BigDecimal getTotalIncome() {
        return totalIncome;
    }

    public BigDecimal getTotalExpense() {
        return totalExpense;
    }
}
