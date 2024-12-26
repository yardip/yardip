/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.party.dao;

import id.my.mdn.kupu.core.base.dao.AbstractFacade;
import id.my.mdn.kupu.core.base.util.Result;
import id.my.mdn.kupu.core.party.entity.Organization;
import id.my.mdn.kupu.core.party.entity.Party;
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
import java.util.Map;

/**
 *
 * @author aphasan
 */
@Stateless
public class PartyFacade extends AbstractFacade<Party> {

    @Inject
    private EntityManager em;

    @Inject
    private PersonFacade personFacade;

    @Inject
    private OrganizationFacade organizationFacade;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PartyFacade() {
        super(Party.class);
    }

    @Override
    public Result<String> create(Party entity) {
        if (entity instanceof Person) {
            entity.setType("Person");
            return personFacade.create((Person) entity);
        } else {
            entity.setType("Organization");
            return organizationFacade.create((Organization) entity);
        }
    }

    @Override
    public Result<String> remove(Party entity) {
        if (entity instanceof Person) {
            entity.setType("Person");
            return personFacade.remove((Person) entity);
        } else {
            entity.setType("Organization");
            return organizationFacade.remove((Organization) entity);
        }
    }

    @Override
    protected From[] selectFind(CriteriaQuery cq, Map<String, Object> parameters) {
        
        Root<Party> party = cq.from(Party.class);
        Join<Party, PartyRole> role = party.join("roles", JoinType.LEFT);
        
        cq.select(party);
        
        return new From[]{party, role};
    }

    @Override
    protected From[] selectCount(CriteriaQuery cq, Map<String, Object> parameters) {
        
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        
        Root<Party> party = cq.from(Party.class);
        Join<Party, PartyRole> role = party.join("roles", JoinType.LEFT);
        
        cq.select(cb.count(party));
        
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
                return cb.like(cb.upper(from[0].get("name")), ("%" + filterValue + "%").toUpperCase());
            case "role":
                return cb.equal(from[1].get("partyRoleType").get("id"), filterValue);
            default:
                return null;
        }
    }

}
