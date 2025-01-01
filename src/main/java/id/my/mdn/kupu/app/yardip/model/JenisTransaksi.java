/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.yardip.model;

import java.math.BigDecimal;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
public enum JenisTransaksi {
    
    PENERIMAAN(new BigDecimal(1), "Pendapatan"),
    PENGELUARAN(new BigDecimal(-1), "Belanja"),
    MUTASI_KAS(new BigDecimal(0), "Mutasi Kas"),
    TRANSFER_SALDO(new BigDecimal(1), "Transfer Saldo");
    
    private final BigDecimal sign;
    
    private final String label;

    private JenisTransaksi(BigDecimal sign, String label) {
        this.sign = sign;
        this.label = label;
    }

    public BigDecimal getSign() {
        return sign;
    }

    public String getLabel() {
        return label;
    }
}
