/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.party.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.TableGenerator;
import java.io.Serializable;

/**
 *
 * @author aphasan
 */
@MappedSuperclass
public abstract class GeographicBoundary implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @TableGenerator(name = "Party_GeographicBoundary", table = "KEYGEN", allocationSize = 1)
    @GeneratedValue(generator = "Party_GeographicBoundary", strategy = GenerationType.TABLE)
    private Long id;

    private String code;

    private String name;

//    @ManyToOne
//    private GeographicBoundary parent;
//
//    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
//    private List<GeographicBoundary> children;

//    public GeographicBoundary getParent() {
//        return parent;
//    }

//    public void setParent(GeographicBoundary parent) {
//        this.parent = parent;
//    }

//    public List<GeographicBoundary> getChildren() {
//        return children;
//    }

//    public void setChildren(List<GeographicBoundary> children) {
//        this.children = children;
//    }
    
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof GeographicBoundary)) {
            return false;
        }
        GeographicBoundary other = (GeographicBoundary) object;
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
