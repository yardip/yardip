/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.core.hr.entity;

import id.my.mdn.kupu.core.base.model.EntityBuilder;
import id.my.mdn.kupu.core.party.entity.BusinessEntity;
import id.my.mdn.kupu.core.party.entity.Person;
import id.my.mdn.kupu.core.party.entity.PersonRole;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Entity
@Table(name = "HR_EMPLOYEE")
public class Employee extends PersonRole implements Serializable {

    private static final long serialVersionUID = 1L;

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends EntityBuilder<Employee> {

        public Builder() {
            super(new Employee());
            entity.setFromDate(LocalDateTime.of(
                    LocalDate.now(), 
                    LocalTime.of(0, 0, 0, 0))
            );
            entity.setSourceRelationships(new ArrayList<>());
            entity.setTargetRelationships(new ArrayList<>());
        }
        
        public Builder forBusinessEntity(BusinessEntity businessEntity) {
            entity.setBusinessEntity(businessEntity);
            return this;
        }

        public Builder withPerson(Person person) {
            if (person.getRoles() == null) {
                person.setRoles(new ArrayList<>());
            }
            entity.setPerson(person);
            person.getRoles().add(entity);

            return this;
        }

    }
    
    @ManyToOne
    private BusinessEntity businessEntity;
    
    @OneToMany(mappedBy = "toRole", cascade = CascadeType.ALL)
    private  List<Employment> positions;

    public BusinessEntity getBusinessEntity() {
        return businessEntity;
    }

    public void setBusinessEntity(BusinessEntity businessEntity) {
        this.businessEntity = businessEntity;
    }

    public List<Employment> getPositions() {
        return positions;
    }

    public void setPositions(List<Employment> positions) {
        this.positions = positions;
    }
    
}
