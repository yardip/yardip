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
@Table(name = "PARTY_REGION")
public class Region extends GeographicBoundary {

    @ManyToOne
    private Country parent;

    @OneToMany(mappedBy = "parent")
    private List<District> children;
    
    @ManyToOne
    private PostalCode postalCode;

    public Country getParent() {
        return parent;
    }

    public void setParent(Country parent) {
        this.parent = parent;
    }

    public List<District> getChildren() {
        return children;
    }

    public void setChildren(List<District> children) {
        this.children = children;
    }

    public PostalCode getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(PostalCode postalCode) {
        this.postalCode = postalCode;
    }
}
