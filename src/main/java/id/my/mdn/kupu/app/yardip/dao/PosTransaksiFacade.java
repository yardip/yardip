/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB40/StatelessEjbClass.java to edit this template
 */
package id.my.mdn.kupu.app.yardip.dao;

import id.my.mdn.kupu.app.yardip.model.JenisTransaksi;
import id.my.mdn.kupu.app.yardip.model.PosTransaksi;
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
import java.util.ArrayList;
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
                if(filterValue == null) return null;
                if (filterValue instanceof Object[]) {
                    return froms[0].get("trxType").in((Object[])filterValue);
                } else {
                    return cb.equal(froms[0].get("trxType"), filterValue);
                }
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
            case "uraian":
                return cb.equal(froms[0].get("uraian"), filterValue);
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

    public void createUniquePosTransaksi(BusinessEntity entity, LocalDate fromDate, LocalDate thruDate, JenisTransaksi trxType, String uraian, BigDecimal target) {

        PosTransaksi posTransaksi = findSingleByAttributes(
                List.of(
                        FilterData.by("entity", entity),
                        FilterData.by("trxType", trxType),
                        FilterData.by("fromDate", fromDate),
                        FilterData.by("thruDate", thruDate),
                        FilterData.by("uraian", uraian)
                )
        );

        if (posTransaksi == null) {
            posTransaksi = new PosTransaksi();
            posTransaksi.setEntity(entity);
            posTransaksi.setTrxType(trxType);
            posTransaksi.setFromDate(fromDate);
            posTransaksi.setThruDate(thruDate);
            posTransaksi.setTarget(target);
            posTransaksi.setUraian(uraian);
            create(posTransaksi);
        }
    }

    public void extendPosTransaksi(BusinessEntity entity, JenisTransaksi... jenisTransaksi) {        
        extendPosTransaksi(entity, LocalDate.now(), jenisTransaksi);
        
    }

    public void extendPosTransaksi(BusinessEntity entity, LocalDate now, JenisTransaksi... jenisTransaksi) { 
        LocalDate endOfPrevYear = now.minusYears(1).withMonth(12).withDayOfMonth(31);

        List<FilterData> filters = new ArrayList<>();
        filters.add(FilterData.by("entity", entity));
        filters.add(FilterData.by("date", endOfPrevYear));
        
        filters.add(FilterData.by("trxType", jenisTransaksi));
        
        List<PosTransaksi> listPosTransaksi = findAll(filters);
        
        for (PosTransaksi pos : listPosTransaksi) {
            createUniquePosTransaksi(
                    pos.getEntity(),
                    pos.getFromDate().withYear(now.getYear()),
                    pos.getThruDate().withYear(now.getYear()),
                    pos.getTrxType(),
                    pos.getUraian(),
                    pos.getTarget());
        }
        
    }

}
