/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.party.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;

/**
 *
 * @author aphasan
 */
@Entity
@Table(name = "PARTY_DISTRICT")
public class District extends GeographicBoundary{

    private static final long serialVersionUID = 1L;
    
    @ManyToOne
    private Region parent;

    @OneToMany(mappedBy = "parent")
    private List<SubDistrict> children;
    
    @ManyToOne
    private PostalCode postalCode;

    public Region getParent() {
        return parent;
    }

    public void setParent(Region parent) {
        this.parent = parent;
    }

    public List<SubDistrict> getChildren() {
        return children;
    }

    public void setChildren(List<SubDistrict> children) {
        this.children = children;
    }

    public PostalCode getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(PostalCode postalCode) {
        this.postalCode = postalCode;
    }
    
}
