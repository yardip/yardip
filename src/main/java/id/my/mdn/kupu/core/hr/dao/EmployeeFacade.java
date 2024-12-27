/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB40/StatelessEjbClass.java to edit this template
 */
package id.my.mdn.kupu.core.hr.dao;

import id.my.mdn.kupu.core.hr.entity.Employee;
import id.my.mdn.kupu.core.party.dao.PersonRoleFacade;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Stateless
public class EmployeeFacade extends PersonRoleFacade<Employee>{
    
    @Inject
    private EntityManager em;

    public EmployeeFacade() {
        super(Employee.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    
}
