/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.party.dao;

import id.my.mdn.kupu.core.base.dao.AbstractFacade;
import id.my.mdn.kupu.core.party.entity.ContactMechanismPurposeType;
import id.my.mdn.kupu.core.party.entity.ContactType;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

/**
 *
 * @author aphasan
 */
@Stateless
public class ContactMechanismPurposeTypeFacade extends AbstractFacade<ContactMechanismPurposeType> {

    @Inject
    private EntityManager em;

    public ContactMechanismPurposeTypeFacade() {
        super(ContactMechanismPurposeType.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public ContactMechanismPurposeType findByContactType(ContactType contactType) {

        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<ContactMechanismPurposeType> cq = cb.createQuery(entityClass);

        Root<ContactMechanismPurposeType> root = cq.from(entityClass);
        cq.select(root).where(
                cb.equal(root.get("contactType"), contactType)
        );

        TypedQuery<ContactMechanismPurposeType> q = getEntityManager().createQuery(cq);

        try {
            return q.getSingleResult();
        } catch (Exception ex) {
            return null;
        }
    }
    
}
