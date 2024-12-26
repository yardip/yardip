/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.party.dao;

import id.my.mdn.kupu.core.base.dao.AbstractFacade;
import id.my.mdn.kupu.core.party.entity.PartyRole;
import id.my.mdn.kupu.core.party.entity.Person;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.From;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author aphasan
 */
@Stateless
public class PersonFacade extends AbstractFacade<Person> {

    @Inject
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PersonFacade() {
        super(Person.class);
    }

    @Override
    public Person createTransient(Map<String, Object> params) {
        Person entity = new Person();
        entity.setRoles(new ArrayList<>());
        
        return entity;
    }

    @Override
    protected From[] selectFind(CriteriaQuery cq, Map<String, Object> parameters) {
        
        Root<Person> party = cq.from(Person.class);
        Join<Person, PartyRole> role = party.join("roles", JoinType.LEFT);
        
        cq.select(party).distinct(true);
        
        return new From[]{party, role};
    }

    @Override
    protected From[] selectCount(CriteriaQuery cq, Map<String, Object> parameters) {
        
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        
        Root<Person> party = cq.from(Person.class);
        Join<Person, PartyRole> role = party.join("roles", JoinType.LEFT);
        
        cq.select(cb.count(party)).distinct(true);
        
        return new From[]{party, role};
    }

    @Override
    protected Predicate applyFilter(String filterName, Object filterValue, CriteriaQuery cq, From... from) {
        if (filterValue == null) {
            return null;
        }
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        switch (filterName) {
            case "name":
                if (filterValue.equals("")) {
                    return null;
                }
                return cb.or(
                        cb.like(cb.upper(from[0].get("firstName")),
                                ("%" + filterValue + "%").toUpperCase()),
                        cb.like(cb.upper(from[0].get("lastName")),
                                ("%" + filterValue + "%").toUpperCase())
                );
            case "role":
                if (filterValue.equals("")) {
                    return null;
                }
                return cb.equal(from[1].get("partyRoleType").get("id"), filterValue);
            default:
                return null;
        }
    }
    
}
