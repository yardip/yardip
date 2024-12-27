/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package id.my.mdn.kupu.core.hr.entity;

import id.my.mdn.kupu.core.base.model.EntityBuilder;
import id.my.mdn.kupu.core.base.model.HierarchicalEntity;
import id.my.mdn.kupu.core.party.entity.BusinessEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.TableGenerator;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Entity
@Table(name = "HR_POSITION")
public class Position implements HierarchicalEntity<Position>, Serializable {

    public static final Builder builder() {
        return new Builder();
    }

    public static final class Builder extends EntityBuilder<Position> {

        public Builder() {
            super(new Position());
        }
        
        public Builder name(String name) {
            entity.setName(name);
            return this;
        }
        
        public Builder businessEntity(BusinessEntity businessEntity) {
            entity.setBusinessEntity(businessEntity);
            return this;
        }

        public Builder withParent(Position parent) {
            if (parent != null) {
                entity.setParent(parent);
                parent.getChildren().add(entity);
            }

            return this;
        }

    }
    
    @Id
    @TableGenerator(name = "HR_Position", table = "KEYGEN", allocationSize = 1)
    @GeneratedValue(generator = "HR_Position", strategy = GenerationType.TABLE)
    private Long id;
    
    @JoinColumn(nullable = false)
    private BusinessEntity businessEntity;
    
    @Column(nullable = false)
    private String name;
    
    @ManyToOne
    @JoinColumn(name = "PARENT_ID", referencedColumnName = "ID")
    private Position parent;
    
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<Position> children;
    
    @OneToMany(mappedBy = "position", cascade = CascadeType.ALL)
    private List<Employment> employees;

    @Override
    public String toString() {
        return id != null ? String.valueOf(id) : null;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.id);
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
        final Position other = (Position) obj;
        return Objects.equals(this.id, other.id);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BusinessEntity getBusinessEntity() {
        return businessEntity;
    }

    public void setBusinessEntity(BusinessEntity businessEntity) {
        this.businessEntity = businessEntity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Position getParent() {
        return parent;
    }

    @Override
    public void setParent(Position parent) {
        this.parent = parent;
    }

    @Override
    public List<Position> getChildren() {
        return children;
    }

    @Override
    public void setChildren(List<Position> children) {
        this.children = children;
    }

    public List<Employment> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employment> employees) {
        this.employees = employees;
    }
    
}
