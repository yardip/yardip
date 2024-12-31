/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.yardip.entity;

import id.my.mdn.kupu.core.party.entity.BusinessEntity;
import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.persistence.SqlResultSetMappings;
import jakarta.persistence.Table;
import jakarta.persistence.TableGenerator;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Entity
@Table(name = "YARDIP_POSTRANSAKSI")
@SqlResultSetMappings({
    @SqlResultSetMapping(
            name = "ProgramKerja",
            classes = {
                @ConstructorResult(
                        targetClass = ProgramKerja.class,
                        columns = {
                            @ColumnResult(name = "ID", type = Long.class),
                            @ColumnResult(name = "URAIAN", type = String.class),
                            @ColumnResult(name = "TARGET", type = BigDecimal.class),
                            @ColumnResult(name = "REALISASI", type = BigDecimal.class),
                            @ColumnResult(name = "CAPAIAN", type = Integer.class)
                        }
                )
            }
    )
})
public class PosTransaksi implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @TableGenerator(name = "Yardip_PosTransaksi", table = "KEYGEN", allocationSize = 1)
    @GeneratedValue(generator = "Yardip_PosTransaksi", strategy = GenerationType.TABLE)
    private Long id;
    
    @ManyToOne
    private BusinessEntity entity;
    
    @Enumerated(EnumType.STRING)
    private JenisTransaksi trxType;
    
    @Lob
    private String uraian;
    
    private LocalDate fromDate;
    
    private LocalDate thruDate;
    
    private BigDecimal target;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BusinessEntity getEntity() {
        return entity;
    }

    public void setEntity(BusinessEntity entity) {
        this.entity = entity;
    }

    public JenisTransaksi getTrxType() {
        return trxType;
    }

    public void setTrxType(JenisTransaksi trxType) {
        this.trxType = trxType;
    }

    public String getUraian() {
        return uraian;
    }

    public void setUraian(String uraian) {
        this.uraian = uraian;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getThruDate() {
        return thruDate;
    }

    public void setThruDate(LocalDate thruDate) {
        this.thruDate = thruDate;
    }

    public BigDecimal getTarget() {
        return target;
    }

    public void setTarget(BigDecimal target) {
        this.target = target;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PosTransaksi)) {
            return false;
        }
        PosTransaksi other = (PosTransaksi) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }
    
}
