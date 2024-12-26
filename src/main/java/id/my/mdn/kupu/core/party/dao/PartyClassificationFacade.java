/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.party.dao;

import id.my.mdn.kupu.core.base.dao.AbstractFacade;
import id.my.mdn.kupu.core.base.util.QueryHelper;
import id.my.mdn.kupu.core.base.util.QueryParameter;
import id.my.mdn.kupu.core.base.util.Result;
import id.my.mdn.kupu.core.party.entity.Party;
import id.my.mdn.kupu.core.party.entity.PartyClassification;
import id.my.mdn.kupu.core.party.entity.PartyClassificationId;
import id.my.mdn.kupu.core.party.entity.PartyType;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.From;
import jakarta.persistence.criteria.Predicate;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author Medina Computama <medina.computama@gmail.com>
 */
@Stateless
public class PartyClassificationFacade extends AbstractFacade<PartyClassification> {

    @Inject
    private EntityManager em;

    @Inject
    private QueryHelper queryHelper;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PartyClassificationFacade() {
        super(PartyClassification.class);
    }

    @Override
    public Result<String> create(PartyClassification entity) {
        Party party = getEntityManager().find(Party.class, entity.getParty().getId());
        Result<String> create = super.create(entity);
        if (create.isSuccess()) {
            getEntityManager().refresh(party);
        }
        return create;
    }

    @Override
    public Result<String> remove(PartyClassification entity) {
        Party party = getEntityManager().find(Party.class, entity.getParty().getId());
        Result<String> remove = super.remove(entity);
        if (remove.isSuccess()) {
            getEntityManager().refresh(party);
        }
        return remove;
    }

    @Override
    protected Predicate applyFilter(String filterName, Object filterValue, CriteriaQuery cq, From... from) {
        if (filterValue == null) {
            return null;
        }
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        switch (filterName) {
            case "party":
                return cb.equal(from[0].get("party"), filterValue);
            default:
                return null;
        }
    }

    private boolean isAlreadyAdded(Party party, PartyType type) {
        long count = queryHelper.queryOne(em,
                "PartyClassification.getExisted",
                Long.class,
                new QueryParameter("party", party),
                new QueryParameter("type", type),
                new QueryParameter("fromDate", LocalDate.now()),
                new QueryParameter("thruDate", LocalDate.now())
        );

        return count > 0;
    }

    public void addClassifications(Party party, List<PartyType> types) {
        types.stream()
                .filter(type -> !isAlreadyAdded(party, type))
                .map(type -> {
                    PartyClassification classification = new PartyClassification();
                    PartyClassificationId id = new PartyClassificationId();
                    id.setFromDate(LocalDate.now());
                    classification.setId(id);
                    classification.setParty(party);
                    party.getClassifications().add(classification);
                    classification.setPartyType(type);
                    return classification;
                })
                .forEach(this::create);

    }

}
