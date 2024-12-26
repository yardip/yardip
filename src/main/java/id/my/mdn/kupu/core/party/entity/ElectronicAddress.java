/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.party.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.io.Serializable;

/**
 *
 * @author aphasan
 */
@Entity
@Table(name = "PARTY_ELECTRONICADDRESS")
public class ElectronicAddress extends ContactMechanism implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String addressString;

    public String getAddressString() {
        return addressString;
    }

    public void setAddressString(String addressString) {
        this.addressString = addressString;
    }

    @Override
    public String getValue() {
        return addressString;
    }
    
}
