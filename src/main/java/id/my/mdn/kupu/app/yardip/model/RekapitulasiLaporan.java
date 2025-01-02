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
public class RekapitulasiLaporan implements Serializable{
    
    private final String entitas;
    
    private final BigDecimal currentYearIncome;

    private final BigDecimal currentMonthIncome;
    
    private final BigDecimal currentYearExpense;

    private final BigDecimal currentMonthExpense;

    public RekapitulasiLaporan(String entitas, BigDecimal currentYearIncome, BigDecimal currentMonthIncome, BigDecimal currentYearExpense, BigDecimal currentMonthExpense) {
        this.entitas = entitas;
        this.currentYearIncome = currentYearIncome;
        this.currentMonthIncome = currentMonthIncome;
        this.currentYearExpense = currentYearExpense;
        this.currentMonthExpense = currentMonthExpense;
    }

    public String getEntitas() {
        return entitas;
    }

    public BigDecimal getCurrentYearIncome() {
        return currentYearIncome;
    }

    public BigDecimal getCurrentMonthIncome() {
        return currentMonthIncome;
    }

    public BigDecimal getCurrentYearExpense() {
        return currentYearExpense;
    }

    public BigDecimal getCurrentMonthExpense() {
        return currentMonthExpense;
    }
}
