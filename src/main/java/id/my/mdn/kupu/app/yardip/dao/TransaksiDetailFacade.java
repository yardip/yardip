/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB40/StatelessEjbClass.java to edit this template
 */
package id.my.mdn.kupu.app.yardip.dao;

import id.my.mdn.kupu.app.yardip.model.TransaksiDetail;
import id.my.mdn.kupu.core.base.dao.AbstractFacade;
import jakarta.ejb.LocalBean;
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
@LocalBean
public class TransaksiDetailFacade extends AbstractFacade<TransaksiDetail> {

    @Inject
    private EntityManager em;

    public TransaksiDetailFacade() {
        super(TransaksiDetail.class);
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
            case "kas":
                return cb.equal(froms[0].get("kas"), filterValue);
            default:
                return null;
        }
    }

    @Override
    protected Expression orderExpression(String field, From... from) {

        switch (field) {
            case "kas":
                return from[0].get("kas").get("id");
            default:
                return null;
        }
    }

}
