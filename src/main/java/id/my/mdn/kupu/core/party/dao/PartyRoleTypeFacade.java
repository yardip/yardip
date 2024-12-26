/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.party.dao;

import id.my.mdn.kupu.core.base.dao.AbstractFacade;
import id.my.mdn.kupu.core.party.entity.PartyRole;
import id.my.mdn.kupu.core.party.entity.PartyRoleType;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.From;
import jakarta.persistence.criteria.Predicate;
import java.util.List;

/**
 *
 * @author aphasan
 */
@Stateless
public class PartyRoleTypeFacade extends AbstractFacade<PartyRoleType> {

    @Inject
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PartyRoleTypeFacade() {
        super(PartyRoleType.class);
    }

    @Override
    protected Predicate applyFilter(String filterName, Object filterValue, CriteriaQuery cq, From... from) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        switch(filterName) {
            case "partyType":
                return cb.or(
                        cb.equal(from[0].get("type"), filterValue),
                        cb.equal(from[0].get("type"), PartyRoleType.class.getSimpleName())
                );
            case "excluded":
                List<String> exclusions = (List<String>) filterValue;
                return cb.not(from[0].get("name").in(exclusions));
            default:
                return null;
        }
    }

    public void createTypeIfNotExist(Class<? extends PartyRole> roleClass, String name) {
        createTypeIfNotExist(roleClass.getSimpleName(), name);
    }

//    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void createTypeIfNotExist(String roleClassName, String name) {
        PartyRoleType roleType = find(roleClassName);
        if (roleType == null) {
            roleType = new PartyRoleType();
            roleType.setId(roleClassName);
            roleType.setName(name);
            create(roleType);
        }
    }

}
