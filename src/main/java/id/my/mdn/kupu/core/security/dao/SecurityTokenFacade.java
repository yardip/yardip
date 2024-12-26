/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.security.dao;

import id.my.mdn.kupu.core.base.dao.AbstractFacade;
import id.my.mdn.kupu.core.security.model.ApplicationUser;
import id.my.mdn.kupu.core.security.model.SecurityToken;
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
public class SecurityTokenFacade extends AbstractFacade<SecurityToken> {

    @Inject
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SecurityTokenFacade() {
        super(SecurityToken.class);
    }

    public SecurityToken findByUser(ApplicationUser user) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<SecurityToken> cq = cb.createQuery(SecurityToken.class);

        Root<SecurityToken> securityTokens = cq.from(SecurityToken.class);

        cq.select(securityTokens).where(cb.equal(
                securityTokens.get("person").get("id"), user.getParty().getId())
        );

        TypedQuery<SecurityToken> q = getEntityManager().createQuery(cq);

        try {
            return q.getSingleResult();
        } catch (Exception ex) {
            return null;
        }
    }

}
