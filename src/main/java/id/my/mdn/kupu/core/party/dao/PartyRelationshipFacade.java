/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.party.dao;

import id.my.mdn.kupu.core.base.dao.AbstractFacade;
import id.my.mdn.kupu.core.party.entity.PartyRelationship;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.From;
import jakarta.persistence.criteria.Predicate;
import java.time.LocalDate;

/**
 *
 * @author aphasan
 * @param <T>
 */
public abstract class PartyRelationshipFacade<T extends PartyRelationship> extends AbstractFacade<T> {

    public PartyRelationshipFacade(Class<T> entityClass) {
        super(entityClass);
    }

    @Override
    protected Predicate applyFilter(String filterName, Object filterValue, CriteriaQuery cq, From... from) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        switch(filterName) {
            case "fromRole":
                return cb.equal(from[0].get("fromRole"), filterValue);
            case "fromRoleId":
                return cb.equal(from[0].get("fromRole").get("id"), filterValue);
            case "toRole":
                return cb.equal(from[0].get("toRole"), filterValue);
            case "toRoleId":
                return cb.equal(from[0].get("toRole").get("id"), filterValue);
            case "ongoing":
                return cb.isNull(from[0].get("thruDate"));
            case "ondate":
                return cb.and(
                        cb.lessThanOrEqualTo(from[0].get("id").<LocalDate>get("fromDate"), (LocalDate) filterValue),                        
                        cb.or(
                                cb.greaterThanOrEqualTo(from[0].<LocalDate>get("thruDate"), (LocalDate) filterValue),
                                cb.isNull(from[0].get("thruDate"))
                        )
                );
            default:
                return null;
        }
    }
    
}
