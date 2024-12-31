/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.yardip.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Entity
@Table(name = "YARDIP_TRANSAKSIDETAIL")
public class TransaksiDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private TransaksiDetailId id;

    @ManyToOne
    @MapsId("transaksi")
    private Transaksi transaksi;

    @ManyToOne
    @MapsId("kas")
    private Kas kas;

    private BigDecimal amount;

    @Transient
    private BigDecimal tmpAmount;

    @Transient
    private StatusMutasiKas statusMutasi;

    public TransaksiDetail() {
    }

    public TransaksiDetail(TransaksiDetailId id) {
        this.id = id;
    }

    public TransaksiDetail(Transaksi transaksi, Kas kas, BigDecimal amount) {
        this.id = new TransaksiDetailId(transaksi.getId(), kas.getId());
        this.transaksi = transaksi;
        this.kas = kas;
        this.amount = amount;
    }

    public TransaksiDetailId getId() {
        return id;
    }

    public void setId(TransaksiDetailId id) {
        this.id = id;
    }

    public Transaksi getTransaksi() {
        return transaksi;
    }

    public void setTransaksi(Transaksi transaksi) {
        this.transaksi = transaksi;
    }

    public Kas getKas() {
        return kas;
    }

    public void setKas(Kas kas) {
        this.kas = kas;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getTmpAmount() {
        if (tmpAmount == null) {
            tmpAmount = amount.abs();
        }
        return tmpAmount;
    }

    public void setTmpAmount(BigDecimal tmpAmount) {
        this.tmpAmount = tmpAmount;
    }

    public StatusMutasiKas getStatusMutasi() {
        return statusMutasi;
    }

    public void setStatusMutasi(StatusMutasiKas statusMutasi) {
        this.statusMutasi = statusMutasi;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 31 * hash + Objects.hashCode(this.id);
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
        final TransaksiDetail other = (TransaksiDetail) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }

}
