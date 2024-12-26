/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.common.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Objects;

/**
 *
 * @author aphasan
 */
@Entity
@Table(name = "COMMON_INTEGERSERIALSEED")
public class IntegerSerialSeed extends SerialSeed<Integer> {

    private static final long serialVersionUID = 1L;
    
    @Id
    private String id;
    
    private String zeroSerial;
    
    private Integer lastSerial;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getZeroSerial() {
        return zeroSerial;
    }

    public void setZeroSerial(String zeroSerial) {
        this.zeroSerial = zeroSerial;
    }

    @Override
    public Integer getLastSerial() {
        return lastSerial;
    }

    public void setLastSerial(Integer lastSerial) {
        this.lastSerial = lastSerial;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.id);
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
        final IntegerSerialSeed other = (IntegerSerialSeed) obj;
        return Objects.equals(this.id, other.id);
    }
    
}
