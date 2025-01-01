/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB40/StatelessEjbClass.java to edit this template
 */
package id.my.mdn.kupu.core.accounting.dao;

import id.my.mdn.kupu.core.accounting.entity.AccountingPeriod;
import id.my.mdn.kupu.core.accounting.entity.AccountingPeriodId;
import id.my.mdn.kupu.core.base.dao.AbstractHierarchicalFacade;
import id.my.mdn.kupu.core.base.util.FilterTypes.FilterData;
import id.my.mdn.kupu.core.party.entity.BusinessEntity;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.From;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Stateless
@LocalBean
public class AccountingPeriodFacade extends AbstractHierarchicalFacade<AccountingPeriod> {

    @Inject
    private EntityManager em;

    public AccountingPeriodFacade() {
        super(AccountingPeriod.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    protected Predicate applyFilter(String filterName, Object filterValue, CriteriaQuery cq, From... froms) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        switch (filterName) {
            case "name":
                return cb.equal(froms[0].get("name"), filterValue);
            case "businessEntity":
                return cb.equal(froms[0].get("businessEntity"), filterValue);
            case "flag":
                return cb.equal(froms[0].get("flag"), filterValue);
            case "fromDate":
                return cb.equal(froms[0].get("id").get("fromDate"), filterValue);
            case "thruDate":
                return cb.equal(froms[0].get("id").get("thruDate"), filterValue);
            case "year":
                int year = (int) filterValue;
                LocalDate begin = LocalDate.of(year, Month.JANUARY, 1);
                LocalDate end = LocalDate.of(year, Month.DECEMBER, 31);
                return cb.and(
                        cb.greaterThanOrEqualTo(froms[0].get("id").get("fromDate"), begin),
                        cb.lessThanOrEqualTo(froms[0].get("id").get("fromDate"), end)
                );
            case "date":
                return cb.and(
                        cb.lessThanOrEqualTo(froms[0].get("id").get("fromDate"), (LocalDate) filterValue),
                        cb.greaterThanOrEqualTo(froms[0].get("id").get("thruDate"), (LocalDate) filterValue)
                );
            default:
                return null;
        }
    }

    public AccountingPeriod getAncestorRoot(AccountingPeriod period) {
        AccountingPeriod current = period;
        AccountingPeriod parent = current.getParent();
        while (parent != null) {
            current = parent;
            parent = current.getParent();
        }
        return current;
    }

    @Override
    protected Expression orderExpression(String field, From... from) {
        switch (field) {
            case "fromDate":
                return from[0].get("fromDate");
            default:
                return null;
        }
    }

    public AccountingPeriod getCurrentPeriod(BusinessEntity businessEntity) {

        LocalDate currentDate = LocalDate.now();
        return getPeriod(businessEntity, currentDate);

    }

    public AccountingPeriod getPeriod(BusinessEntity businessEntity, LocalDate date) {

        AccountingPeriod result = findSingleByAttributes(List.of(
                FilterData.by("businessEntity", businessEntity),
                FilterData.by("date", date)
        ));

        return result;

    }

    public AccountingPeriod getPreviousPeriod(BusinessEntity businessEntity, AccountingPeriod currentPeriod) {

        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();

        CriteriaQuery<AccountingPeriod> cq = cb.createQuery(AccountingPeriod.class);

        Root<AccountingPeriod> root = cq.from(AccountingPeriod.class);

        Subquery<LocalDate> sq = cq.subquery(LocalDate.class);
        Root<AccountingPeriod> sqroot = sq.from(AccountingPeriod.class);
        sq.select(cb.greatest(sqroot.get("id").<LocalDate>get("fromDate")))
                .where(
                        cb.equal(sqroot.get("businessEntity"), businessEntity),
                        cb.equal(sqroot.get("flag"), currentPeriod.getFlag()),
                        cb.lessThan(sqroot.get("id").get("fromDate"), currentPeriod.getFromDate()),
                        cb.lessThan(sqroot.get("id").get("thruDate"), currentPeriod.getThruDate())
                );

        cq.select(root)
                .where(
                        cb.equal(root.get("businessEntity"), businessEntity),
                        cb.equal(root.get("flag"), currentPeriod.getFlag()),
                        cb.equal(root.get("id").get("fromDate"), sq)
                );

        TypedQuery<AccountingPeriod> q = getEntityManager().createQuery(cq);

        try {
            return q.getSingleResult();
        } catch (Exception ex) {
            return null;
        }
    }

    public void generateMonthlyCalendarPeriod(BusinessEntity entity, String flag) {

        int year = LocalDate.now().getYear();
        LocalDate begin = LocalDate.of(year, 1, 1);
        LocalDate end = LocalDate.of(year, 12, 31);

        for (LocalDate i = begin;;) {
            if (i.isAfter(end)) {
                break;
            }
            LocalDate periodBegin = i;
            LocalDate periodEnd = i.plusMonths(1).withDayOfMonth(1).minusDays(1);
            String periodName = periodBegin.format(DateTimeFormatter.ofPattern("MMMM yyyy", Locale.forLanguageTag("id")));

            AccountingPeriod period = findSingleByAttributes(
                    List.of(
                            FilterData.by("name", periodName),
                            FilterData.by("businessEntity", entity),
                            FilterData.by("fromDate", periodBegin),
                            FilterData.by("thruDate", periodEnd)
                    )
            );
            if (period == null) {
                period = new AccountingPeriod();
                period.setId(new AccountingPeriodId());
                period.setFromDate(periodBegin);
                period.setThruDate(periodEnd);
                period.setName(periodName);
                period.setBusinessEntity(entity);
                period.setFlag(flag);

                create(period);
            }

            i = i.plusMonths(1).withDayOfMonth(1);
        }
    }

    public List<Integer> getAvailableYears(BusinessEntity entity) {

        String sql = """
                     SELECT DISTINCT(YEAR(ap0.fromdate))
                     FROM accounting_accountingperiod AS ap0
                     WHERE ap0.businessentity_id = ?
                     """;

        Query query = em.createNativeQuery(sql);
        query.setParameter(1, entity.getId());

        return query.getResultList();
    }

}
