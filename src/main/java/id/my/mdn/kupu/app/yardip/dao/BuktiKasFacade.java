/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB40/StatelessEjbClass.java to edit this template
 */
package id.my.mdn.kupu.app.yardip.dao;

import id.my.mdn.kupu.app.yardip.entity.BuktiKas;
import id.my.mdn.kupu.core.base.dao.AbstractFacade;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.From;
import jakarta.persistence.criteria.Predicate;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Stateless
public class BuktiKasFacade extends AbstractFacade<BuktiKas> {

    @Inject
    private EntityManager em;

    public BuktiKasFacade() {
        super(BuktiKas.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    protected Predicate applyFilter(String filterName, Object filterValue, CriteriaQuery cq, From... froms) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        switch (filterName) {
            case "transaksi":
                return cb.equal(froms[0].get("transaksi"), filterValue);
            default:
                return super.applyFilter(filterName, filterValue, cq, froms);
        }
    }

    @Override
    protected Expression orderExpression(String field, From... from) {
        switch (field) {
            case "seq":
                return from[0].get("id").get("seq");
            default:
                return null;
        }
    }

}
