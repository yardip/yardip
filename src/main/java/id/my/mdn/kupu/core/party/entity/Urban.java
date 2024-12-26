/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.party.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 *
 * @author aphasan
 */
@Entity
@Table(name = "PARTY_URBAN")
public class Urban extends GeographicBoundary {
    private static final long serialVersionUID = 1L;
    
    @ManyToOne
    private SubDistrict parent;
    
    @ManyToOne
    private PostalCode postalCode;

    public SubDistrict getParent() {
        return parent;
    }

    public void setParent(SubDistrict parent) {
        this.parent = parent;
    }

    public PostalCode getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(PostalCode postalCode) {
        this.postalCode = postalCode;
    }
    
}
