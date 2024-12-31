/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB40/StatelessEjbClass.java to edit this template
 */
package id.my.mdn.kupu.app.yardip.dao;

import id.my.mdn.kupu.app.yardip.entity.JenisTransaksi;
import id.my.mdn.kupu.app.yardip.entity.PosTransaksi;
import id.my.mdn.kupu.core.base.dao.AbstractFacade;
import id.my.mdn.kupu.core.base.util.FilterTypes.FilterData;
import id.my.mdn.kupu.core.party.entity.BusinessEntity;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.From;
import jakarta.persistence.criteria.Predicate;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Stateless
public class PosTransaksiFacade extends AbstractFacade<PosTransaksi> {

    @Inject
    private EntityManager em;

    public PosTransaksiFacade() {
        super(PosTransaksi.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    protected Predicate applyFilter(String filterName, Object filterValue, CriteriaQuery cq, From... froms) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        switch (filterName) {
            case "entity":
                return cb.equal(froms[0].get("entity"), filterValue);
            case "trxType":
                return cb.equal(froms[0].get("trxType"), filterValue);
            case "date":
                return cb.and(
                        cb.lessThanOrEqualTo(
                                froms[0].<LocalDate>get("fromDate"),
                                (LocalDate) filterValue
                        ),
                        cb.or(cb.isNull(froms[0].<LocalDate>get("thruDate")),
                                cb.greaterThanOrEqualTo(
                                        froms[0].<LocalDate>get("thruDate"),
                                        (LocalDate) filterValue
                                )
                        )
                );
            case "fromDate":
                return cb.equal(
                        froms[0].<LocalDate>get("fromDate"),
                        (LocalDate) filterValue
                );
            case "thruDate":
                return cb.equal(
                        froms[0].<LocalDate>get("thruDate"),
                        (LocalDate) filterValue
                );
            default:
                return super.applyFilter(filterName, filterValue, cq, froms);
        }
    }
    
    public void createUniquePosTransaksi(BusinessEntity entity, JenisTransaksi trxType, String remarks) {

        PosTransaksi posTransaksi = findSingleByAttributes(
                List.of(
                        FilterData.by("entity", entity),
                        FilterData.by("trxType", trxType)
                )
        );

        if (posTransaksi == null) {
            posTransaksi = new PosTransaksi();
            posTransaksi.setEntity(entity);
            posTransaksi.setTrxType(trxType);
            posTransaksi.setFromDate(LocalDate.EPOCH);
            posTransaksi.setTarget(BigDecimal.ZERO);
            posTransaksi.setUraian(remarks);
            create(posTransaksi);
        }
    }

}
