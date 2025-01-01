/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package id.my.mdn.kupu.app.yardip.model;

import java.math.BigDecimal;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
public enum StatusMutasiKas {
    SOURCE(new BigDecimal(-1), "Sumber"),
    DESTINATION(new BigDecimal(1), "Tujuan");
    
    private final BigDecimal sign;
    
    private final String label;

    private StatusMutasiKas(BigDecimal sign, String label) {
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
