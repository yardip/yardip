/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB40/StatelessEjbClass.java to edit this template
 */
package id.my.mdn.kupu.core.party.dao;

import id.my.mdn.kupu.core.party.entity.SubsidiaryOrganization;
import jakarta.ejb.LocalBean;
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
@LocalBean
public class SubsidiaryOrganizationFacade extends AbstractPartyRoleFacade<SubsidiaryOrganization> {
    
    @Inject
    private EntityManager em;

    @Inject
    private PartyRoleTypeFacade roleTypeFacade;

    public SubsidiaryOrganizationFacade() {
        super(SubsidiaryOrganization.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    protected PartyRoleTypeFacade getRoleTypeFacade() {
        return roleTypeFacade;
    }

    @Override
    protected Predicate applyFilter(String filterName, Object filterValue, 
            CriteriaQuery cq, From... from) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        switch (filterName) {
            case "organization":
                return cb.equal(from[0].get("party"), filterValue);
            default:
                return null;
        }
    }

    
}
