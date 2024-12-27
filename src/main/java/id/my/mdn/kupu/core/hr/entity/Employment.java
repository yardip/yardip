/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.core.hr.entity;

import id.my.mdn.kupu.core.party.entity.BusinessEntity;
import id.my.mdn.kupu.core.party.entity.PartyRelationship;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Entity
@Table(name = "HR_EMPLOYMENT")
@DiscriminatorValue("Employment")
public class Employment extends PartyRelationship {

    private static final long serialVersionUID = 1L;
    
    @ManyToOne
    private Position position;   

    public BusinessEntity getBusinessEntity() {
        return (BusinessEntity) getFromRole();
    }

    public void setBusinessEntity(BusinessEntity employee) {
        setFromRole(employee);
    }

    public Employee getEmployee() {
        return (Employee) getToRole();
    }

    public void setEmployee(Employee employee) {
        setToRole(employee);
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

}
