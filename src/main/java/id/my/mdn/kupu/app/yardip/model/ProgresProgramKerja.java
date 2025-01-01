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
public class ProgresProgramKerja implements Serializable {
    
    private final String programKerja;
    
    private final BigDecimal currentYearAmount;

    private final BigDecimal currentMonthAmount;

    public ProgresProgramKerja(String programKerja, BigDecimal currentYearAmount, BigDecimal currentMonthAmount) {
        this.programKerja = programKerja;
        this.currentYearAmount = currentYearAmount != null ? currentYearAmount : BigDecimal.ZERO;
        this.currentMonthAmount = currentMonthAmount != null ? currentMonthAmount : BigDecimal.ZERO;
    }

    public String getProgramKerja() {
        return programKerja;
    }

    public BigDecimal getCurrentYearAmount() {
        return currentYearAmount;
    }

    public BigDecimal getCurrentMonthAmount() {
        return currentMonthAmount;
    }
    
}
