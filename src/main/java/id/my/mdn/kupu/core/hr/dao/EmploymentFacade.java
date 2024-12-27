/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB40/StatelessEjbClass.java to edit this template
 */
package id.my.mdn.kupu.core.hr.dao;

import id.my.mdn.kupu.core.base.util.Result;
import id.my.mdn.kupu.core.hr.entity.Employment;
import id.my.mdn.kupu.core.hr.entity.Position;
import id.my.mdn.kupu.core.party.dao.PartyFacade;
import id.my.mdn.kupu.core.party.dao.PartyRelationshipFacade;
import id.my.mdn.kupu.core.party.entity.Person;
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

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Stateless
public class EmploymentFacade extends PartyRelationshipFacade<Employment> {

    @Inject
    private EntityManager em;

    public EmploymentFacade() {
        super(Employment.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Inject
    private PartyFacade partyFacade;

    @Override
    public Result<String> create(Employment entity) {

        Person person = (Person) entity.getEmployee().getPerson();

        if (person.getId() == null) {
            return partyFacade.create(entity.getEmployee().getPerson());

        } else {
            return partyFacade.edit(entity.getEmployee().getPerson());
        }

    }

    @Override
    public Result<String> edit(Employment entity) {
        return partyFacade.edit(entity.getEmployee().getPerson());
    }

    public Employment getExclusiveEmployment(Position position, LocalDate date) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Employment> cq = cb.createQuery(Employment.class);
        Root<Employment> employment = cq.from(Employment.class);

        cq.select(employment).where(cb.equal(employment.get("position"), position),
                cb.lessThanOrEqualTo(employment.get("id").get("fromDate"), date),
                cb.or(cb.greaterThanOrEqualTo(employment.get("thruDate"), date),
                        cb.isNull(employment.get("thruDate"))
                )
        );

        TypedQuery<Employment> q = getEntityManager().createQuery(cq);

        try {
            Employment result = q.getSingleResult();
            return result;
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    protected Predicate applyFilter(String filterName, Object filterValue, CriteriaQuery cq, From... froms) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        switch (filterName) {
            case "position":
                return cb.equal(froms[0].get("position"), filterValue);
            default:
                return super.applyFilter(filterName, filterValue, cq, froms);
        }
    }

    @Override
    protected Expression orderExpression(String field, From... from) {
        switch (field) {
            case "fromDate":
                return from[0].get("id").get("fromDate");
            default:
                return super.orderExpression(field, from);
        }
    }

}
