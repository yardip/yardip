/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB40/StatelessEjbClass.java to edit this template
 */
package id.my.mdn.kupu.core.hr.dao;

import id.my.mdn.kupu.core.base.dao.AbstractFacade;
import id.my.mdn.kupu.core.hr.entity.Position;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.From;
import jakarta.persistence.criteria.Predicate;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Stateless
public class PositionAltFacade extends AbstractFacade<Position> {
    
    @Inject
    private EntityManager em;

    public PositionAltFacade() {
        super(Position.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    protected Predicate applyFilter(String filterName, Object filterValue, CriteriaQuery cq, From... from) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        switch (filterName) {
            case "businessEntity":
                return cb.equal(from[0].get("businessEntity"), filterValue);
            default:
                return super.applyFilter(filterName, filterValue, cq, from);
        }
    }

    
}
