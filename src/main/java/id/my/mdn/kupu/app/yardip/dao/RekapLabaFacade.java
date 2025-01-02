/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.yardip.dao;

import id.my.mdn.kupu.app.yardip.model.RekapitulasiLaba;
import id.my.mdn.kupu.core.base.dao.AbstractSqlFacade;
import id.my.mdn.kupu.core.base.util.Constants;
import id.my.mdn.kupu.core.party.entity.BusinessEntity;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

/**
 *
 * @author aphasan
 */
@Stateless
public class RekapLabaFacade extends AbstractSqlFacade<RekapitulasiLaba> {

    private static final String FIND_ALL = """
    SELECT AP1.NAME, 
           SUM(CASE WHEN TRX1.TRXTYPE = 'PENERIMAAN' THEN TRX1.AMOUNT ELSE 0 END) AS JML_PENERIMAAN,
           SUM(CASE WHEN TRX1.TRXTYPE = 'PENGELUARAN' THEN TRX1.AMOUNT ELSE 0 END) AS JML_PENGELUARAN
    FROM (
        SELECT AP0.NAME, AP0.FROMDATE, AP0.THRUDATE
        FROM ACCOUNTING_ACCOUNTINGPERIOD AP0
        WHERE AP0.BUSINESSENTITY_ID = ?
        AND AP0.FROMDATE >= ? AND AP0.THRUDATE <= ?
    ) AS AP1
    LEFT JOIN (
        SELECT TRX0.TRXDATE, POS0.TRXTYPE, TRX0.AMOUNT
        FROM YARDIP_TRANSAKSI AS TRX0
        JOIN YARDIP_POSTRANSAKSI AS POS0
        ON TRX0.TRXTYPE_ID = POS0.ID
        WHERE TRX0.BUSINESSENTITY_ID = ?
        AND (TRX0.TRXDATE >= ? AND TRX0.TRXDATE <= ?)
        AND (POS0.TRXTYPE = 'PENERIMAAN' OR POS0.TRXTYPE = 'PENGELUARAN')
    ) AS TRX1
    ON TRX1.TRXDATE >= AP1.FROMDATE AND TRX1.TRXDATE <= AP1.THRUDATE
    GROUP BY AP1.NAME
    """;

    @Inject
    private EntityManager em;

    public RekapLabaFacade() {
        super(RekapitulasiLaba.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    protected String getFindAllQuery() {
        return FIND_ALL;
    }

    @Override
    protected void setParameters(Query q, Map<String, Object> parameters) {
        BusinessEntity businessEntity = (BusinessEntity) parameters.get("businessEntity");

        q.setParameter(1, businessEntity.getId());
        q.setParameter(4, businessEntity.getId());

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern(Constants.KEYFORMAT_LOCALDATE_DEFAULT);

        int year = (Integer) parameters.get("year");

        String yearBegin = fmt.format(LocalDate.now().withDayOfMonth(1).withMonth(1).withYear(year));
        String yearEnd = fmt.format(LocalDate.now().withDayOfMonth(31).withMonth(12).withYear(year));

        q.setParameter(2, yearBegin);
        q.setParameter(3, yearEnd);
        q.setParameter(5, yearBegin);
        q.setParameter(6, yearEnd);

    }

}
