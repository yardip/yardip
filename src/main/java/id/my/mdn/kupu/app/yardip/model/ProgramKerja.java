/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.yardip.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
public class ProgramKerja implements Serializable {
    
    private final Long id;
    
    private final String uraian;
    
    private final BigDecimal target;
    
    private final BigDecimal realisasi;
    
    private final Integer capaian;

    public ProgramKerja(Long id) {
        this.id = id;
        this.uraian = "";
        this.target = BigDecimal.ZERO;
        this.realisasi = BigDecimal.ZERO;
        this.capaian = 100;
    }

    public ProgramKerja(Long id, String uraian, BigDecimal target, BigDecimal realisasi, Integer capaian) {
        this.id = id;
        this.uraian = uraian;
        this.target = target;
        this.realisasi = realisasi;
        this.capaian = capaian;
    }

    public Long getId() {
        return id;
    }

    public String getUraian() {
        return uraian;
    }

    public BigDecimal getTarget() {
        return target.abs();
    }

    public BigDecimal getRealisasi() {
        return realisasi.abs();
    }

    public Integer getCapaian() {
        return Math.abs(capaian);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.id);
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
        final ProgramKerja other = (ProgramKerja) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }
    
}
