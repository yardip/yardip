/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.yardip.model;

import id.my.mdn.kupu.core.base.util.EntityUtil;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Embeddable
public class TransaksiDetailId implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String transaksi;
    
    private Long kas;

    public TransaksiDetailId() {
    }

    public TransaksiDetailId(String transaksi, Long kas) {
        this.transaksi = transaksi;
        this.kas = kas;
    }

    public TransaksiDetailId(String... params) {
        this(params[0], Long.valueOf(params[1]));
    }

    public String getTransaksi() {
        return transaksi;
    }

    public void setTransaksi(String transaksi) {
        this.transaksi = transaksi;
    }

    public Long getKas() {
        return kas;
    }

    public void setKas(Long kas) {
        this.kas = kas;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + Objects.hashCode(this.transaksi);
        hash = 53 * hash + Objects.hashCode(this.kas);
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
        final TransaksiDetailId other = (TransaksiDetailId) obj;
        if (!Objects.equals(this.transaksi, other.transaksi)) {
            return false;
        }
        return Objects.equals(this.kas, other.kas);
    }

    @Override
    public String toString() {
        return EntityUtil.createStringId(transaksi, kas);
    }
    
}
