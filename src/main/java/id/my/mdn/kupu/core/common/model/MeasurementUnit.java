/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.common.model;

import id.my.mdn.kupu.core.base.model.EntityBuilder;
import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import jakarta.persistence.TableGenerator;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author aphasan
 */
@Entity
@Table(name = "COMMON_MEASUREMENTUNIT")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class MeasurementUnit implements Serializable {

    private static final long serialVersionUID = 1L;
    
    public static class Builder<T extends MeasurementUnit> extends EntityBuilder<T> {
        
        public Builder(Class<T> measurementType) throws NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            super(measurementType.getDeclaredConstructor().newInstance());
        }
        
        public Builder code(String code) {
            entity.setCode(code);
            return this;
        }
        
        public Builder name(String name) {
            entity.setName(name);
            return this;
        }
        
        public Builder remarks(String remarks) {
            entity.setRemarks(remarks);
            return this;
        }
        
    }
    
    public static <T extends MeasurementUnit> Builder<T> builder(Class<T> measurementType) {
        try {
            return new Builder<T>(measurementType);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(MeasurementUnit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    @Id
    @TableGenerator(name = "Common_MeasurementUnit", table = "KEYGEN")
    @GeneratedValue(generator = "Common_MeasurementUnit", strategy = GenerationType.TABLE)
    private Long id;
    
    @Column(unique=true)
    private String code;
    
    @Column(unique = true)
    private String name;
    
    private String remarks;

    @JsonbTransient
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @JsonbTransient
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonbTransient
    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    public int hashCode() {
        int hash = 5;
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
        final MeasurementUnit other = (MeasurementUnit) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }
    
}
