/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.party.dao;

import id.my.mdn.kupu.core.base.dao.AbstractFacade;
import id.my.mdn.kupu.core.party.entity.Organization;
import id.my.mdn.kupu.core.party.entity.PartyRole;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author aphasan
 */
@Stateless
public class OrganizationFacade extends AbstractFacade<Organization> {

    @Inject
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public OrganizationFacade() {
        super(Organization.class);
    }

    @Override
    public Organization createTransient(Map<String, Object> params) {
        Organization entity = new Organization();
        entity.setRoles(new ArrayList<>());
        
        return entity;
    }
    
    public Organization getCompany() {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Organization> cq = cb.createQuery(Organization.class);
        
        Root<Organization> organization = cq.from(Organization.class);
        Join<Organization, PartyRole> role = organization.join("roles");
        
        cq.select(organization);
        
        TypedQuery<Organization> q = getEntityManager().createQuery(cq);
        
        try {
            return q.getSingleResult();
        } catch (Exception ex) {
            return null;
        }
    }

    public byte[] getLogo(Long entityId) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery();
        Root<Organization> root = cq.from(Organization.class);

        cq.select(root.get("logo"))
                .where(cb.equal(root.get("id"), entityId));

        Query q = em.createQuery(cq);
        try {
            return (byte[]) q.getSingleResult();
        } catch (Exception ex) {
            return null;
        }
    }
    
}
