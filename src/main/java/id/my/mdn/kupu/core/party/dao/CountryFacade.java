/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.party.dao;

import id.my.mdn.kupu.core.party.entity.Country;
import id.my.mdn.kupu.core.party.entity.GeographicBoundary;
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
public class CountryFacade extends GeographicBoundaryFacade<Country> {

    @Inject
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CountryFacade() {
        super(Country.class);
    }
    
//    public PostalCode getPostalCode(Country country) {
//        return getParent(country, PostalCode.class);
//    }

    public Country findByName(String name, GeographicBoundary parent) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Country> cq = cb.createQuery(entityClass);
        Root<Country> root = cq.from(entityClass);

        cq.select(root).where(
                cb.equal(root.get("name"), name)
        );

        TypedQuery<Country> q = getEntityManager().createQuery(cq);

        try {
            return q.getSingleResult();
        } catch (Exception ex) {
            return null;
        }
    }
    
}
