/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.yardip.entity;

import id.my.mdn.kupu.core.base.util.EntityUtil;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Embeddable
public class BuktiKasId implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String transaksi;
    
    private Integer seq;

    public BuktiKasId() {
    }

    public BuktiKasId(String transaksi, Integer seq) {
        this.transaksi = transaksi;
        this.seq = seq;
    }

    public BuktiKasId(String... params) {
        this(params[0], Integer.valueOf(params[1]));
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.transaksi);
        hash = 97 * hash + Objects.hashCode(this.seq);
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
        final BuktiKasId other = (BuktiKasId) obj;
        if (!Objects.equals(this.transaksi, other.transaksi)) {
            return false;
        }
        return Objects.equals(this.seq, other.seq);
    }

    @Override
    public String toString() {
        return EntityUtil.createStringId(transaksi, seq);
    }

    public String getTransaksi() {
        return transaksi;
    }

    public void setTransaksi(String transaksi) {
        this.transaksi = transaksi;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }
    
    
    
}
