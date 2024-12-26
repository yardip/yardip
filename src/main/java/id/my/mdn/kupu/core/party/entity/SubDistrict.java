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
@Table(name = "PARTY_SUBDISTRICT")
public class SubDistrict extends GeographicBoundary {

    private static final long serialVersionUID = 1L;
    
    @ManyToOne
    private District parent;

    @OneToMany(mappedBy = "parent")
    private List<Urban> children;
    
    @ManyToOne
    private PostalCode postalCode;

    public District getParent() {
        return parent;
    }

    public void setParent(District parent) {
        this.parent = parent;
    }

    public List<Urban> getChildren() {
        return children;
    }

    public void setChildren(List<Urban> children) {
        this.children = children;
    }

    public PostalCode getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(PostalCode postalCode) {
        this.postalCode = postalCode;
    }
    
}
