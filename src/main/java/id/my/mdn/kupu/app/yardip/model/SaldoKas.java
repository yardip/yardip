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
public class SaldoKas {
    
    private final Long id;
    
    private final String name;
    
    private final String identifier;
    
    private BigDecimal amount;

    public SaldoKas(Long id, String name, String identifier, BigDecimal amount) {
        this.id = id;
        this.name = name;
        this.identifier = (identifier == null || identifier.isBlank()) ? identifier : ("Rek. " + identifier);
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getIdentifier() {
        return identifier;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
