/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.yardip.entity;

import id.my.mdn.kupu.core.party.entity.BusinessEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.persistence.SqlResultSetMappings;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Entity
@Table(name = "YARDIP_TRANSAKSI")
@SqlResultSetMappings({
    //    @SqlResultSetMapping(
    //            name = "RangkumanTransaksi",
    //            classes = {
    //                @ConstructorResult(
    //                        targetClass = RangkumanTransaksi.class,
    //                        columns = {
    //                            @ColumnResult(name = "id", type = String.class),
    //                            @ColumnResult(name = "trxdate", type = LocalDate.class),
    //                            @ColumnResult(name = "uraian", type = String.class),
    //                            @ColumnResult(name = "trxdocid", type = String.class),
    //                            @ColumnResult(name = "amount", type = BigDecimal.class),
    //                            @ColumnResult(name = "kas1", type = BigDecimal.class),
    //                            @ColumnResult(name = "kas2", type = BigDecimal.class),
    //                            @ColumnResult(name = "kas3", type = BigDecimal.class)
    //                        }
    //                )
    //            }
    //    ),
    @SqlResultSetMapping(
            name = "SaldoKas",
            classes = {
                @ConstructorResult(
                        targetClass = SaldoKas.class,
                        columns = {
                            @ColumnResult(name = "ID", type = Long.class),
                            @ColumnResult(name = "NAME", type = String.class),
                            @ColumnResult(name = "IDENTIFIER", type = String.class),
                            @ColumnResult(name = "AMOUNT", type = BigDecimal.class)
                        }
                )
            }
    )
})
public class Transaksi implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @ManyToOne
    private BusinessEntity businessEntity;

    private LocalDate trxDate;

    @ManyToOne
    private PosTransaksi trxType;

    private BigDecimal amount;

    @Lob
    private String uraian;

    private String trxDocId;

    @OneToMany(mappedBy = "transaksi", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BuktiKas> trxDocs;

    @OneToMany(mappedBy = "transaksi", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TransaksiDetail> detailTransaksi;

    private LocalDateTime created;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BusinessEntity getBusinessEntity() {
        return businessEntity;
    }

    public void setBusinessEntity(BusinessEntity businessEntity) {
        this.businessEntity = businessEntity;
    }

    public LocalDate getTrxDate() {
        return trxDate;
    }

    public void setTrxDate(LocalDate trxDate) {
        this.trxDate = trxDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getTrxDocId() {
        return trxDocId;
    }

    public void setTrxDocId(String trxDocId) {
        this.trxDocId = trxDocId;
    }

    public List<BuktiKas> getTrxDocs() {
        return trxDocs;
    }

    public void setTrxDocs(List<BuktiKas> trxDocs) {
        this.trxDocs = trxDocs;
    }

    public String getUraian() {
        return uraian;
    }

    public void setUraian(String uraian) {
        this.uraian = uraian;
    }

    public PosTransaksi getTrxType() {
        return trxType;
    }

    public void setTrxType(PosTransaksi trxType) {
        this.trxType = trxType;
    }

    public List<TransaksiDetail> getDetailTransaksi() {
        return detailTransaksi;
    }

    public void setDetailTransaksi(List<TransaksiDetail> detailTransaksi) {
        this.detailTransaksi = detailTransaksi;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    @PrePersist
    private void prePersist() {
        id = UUID.randomUUID().toString();
        if (created == null) {
            created = LocalDateTime.now();
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.id);
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
        final Transaksi other = (Transaksi) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return id;
    }

}
