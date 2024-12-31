/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB40/StatelessEjbClass.java to edit this template
 */
package id.my.mdn.kupu.app.yardip.dao;

import id.my.mdn.kupu.app.yardip.entity.Kas;
import id.my.mdn.kupu.core.accounting.entity.AccountingPeriod;
import id.my.mdn.kupu.core.base.dao.AbstractFacade;
import id.my.mdn.kupu.core.base.util.FilterTypes.FilterData;
import id.my.mdn.kupu.core.party.entity.BusinessEntity;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.From;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Stateless
@LocalBean
public class KasFacade extends AbstractFacade<Kas> {

    @Inject
    private EntityManager em;

    public KasFacade() {
        super(Kas.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public void createKasIfNotExist(BusinessEntity owner, String name, String identifier, LocalDate openAccountDate) {
        Kas kas = findSingleByAttributes(List.of(
                FilterData.by("owner", owner),
                FilterData.by("name", name),
                FilterData.by("identifier", identifier)
        ));
        
        if(kas == null) {
            kas = new Kas();
            kas.setOwner(owner);
            kas.setName(name);
            kas.setIdentifier("");
            kas.setFromDate(openAccountDate);
            
            create(kas);
        }
    }

    public List<String> findDistinctKasByName(BusinessEntity entity, AccountingPeriod period) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<String> cq = cb.createQuery(String.class);

        Root<Kas> root = cq.from(Kas.class);

        cq.select(root.get("name")).distinct(true)
                .where(
                        cb.equal(root.get("owner"), entity),
                        cb.lessThanOrEqualTo(root.get("fromDate"), period.getFromDate()),
                        cb.or(
                                cb.greaterThanOrEqualTo(root.get("thruDate"), root.get("fromDate")),
                                cb.isNull(root.get("thruDate"))
                        )
                )
                .orderBy(cb.asc(root.get("id")));

        TypedQuery<String> q = em.createQuery(cq);

        return q.getResultList();
    }

    @Override
    protected Predicate applyFilter(String filterName, Object filterValue, CriteriaQuery cq, From... froms) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        switch (filterName) {
            case "entity":
            case "owner":
                return cb.equal(froms[0].get("owner"), filterValue);
            case "name":
                return cb.equal(froms[0].get("name"), filterValue);
            case "identifier":
                return cb.equal(froms[0].get("identifier"), filterValue);
            case "date":
                return cb.and(
                        cb.lessThanOrEqualTo(froms[0].get("fromDate"), (LocalDate) filterValue),
                        cb.or(
                                cb.greaterThanOrEqualTo(froms[0].get("thruDate"), (LocalDate) filterValue),
                                cb.isNull(froms[0].get("thruDate"))
                        )
                );
            default:
                return super.applyFilter(filterName, filterValue, cq, froms);
        }
    }

    @Override
    protected Expression orderExpression(String field, From... from) {
        switch (field) {
            case "id":
                return from[0].get("id");
            case "name":
                return from[0].get("name");
            default:
                return null;
        }
    }

}
