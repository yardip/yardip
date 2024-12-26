/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.party.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;

/**
 *
 * @author aphasan
 */
@Entity
@Table(name = "PARTY_COUNTRY")
public class Country extends GeographicBoundary {

    private static final long serialVersionUID = 1L;

    @OneToMany(mappedBy = "parent")
    private List<Region> children;

    public List<Region> getChildren() {
        return children;
    }

    public void setChildren(List<Region> children) {
        this.children = children;
    }
    
}
