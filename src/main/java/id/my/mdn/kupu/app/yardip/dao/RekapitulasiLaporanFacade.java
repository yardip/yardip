/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.yardip.dao;

import id.my.mdn.kupu.app.yardip.model.RekapitulasiLaporan;
import id.my.mdn.kupu.core.accounting.entity.AccountingPeriod;
import id.my.mdn.kupu.core.base.dao.AbstractSqlFacade;
import id.my.mdn.kupu.core.base.util.Constants;
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
public class RekapitulasiLaporanFacade extends AbstractSqlFacade<RekapitulasiLaporan> {

    private static final String FIND_ALL = """
    SELECT ORG0.NAME, RANG3.SISA_AWAL, RANG3.PENERIMAAN_PREV, RANG3.PENERIMAAN_CURR, RANG3.PENGELUARAN_PREV, RANG3.PENGELUARAN_CURR
    FROM (
        SELECT ROL0.PARTY_ID, 
               (CASE WHEN RANG2.SISA_AWAL IS NULL THEN 0 ELSE RANG2.SISA_AWAL END) AS SISA_AWAL, 
               RANG2.PENERIMAAN_PREV, RANG2.PENERIMAAN_CURR, 
               RANG2.PENGELUARAN_PREV, RANG2.PENGELUARAN_CURR
        FROM (
                SELECT RANG1.ENTITY_ID,
                       (
                            SELECT TRX.AMOUNT
                            FROM YARDIP_TRANSAKSI AS TRX
                            JOIN YARDIP_POSTRANSAKSI AS POS
                            ON TRX.TRXTYPE_ID = POS.ID
                            WHERE TRX.BUSINESSENTITY_ID = RANG1.ENTITY_ID
                            AND POS.TRXTYPE = 'TRANSFER_SALDO'       
                            AND TRX.TRXDATE = ?
                       ) AS SISA_AWAL,
                       SUM (CASE WHEN RANG1.TRXTYPE = 'PENERIMAAN' THEN RANG1.PREV_TOTAL ELSE 0 END) AS PENERIMAAN_PREV, 
                       SUM (CASE WHEN RANG1.TRXTYPE = 'PENERIMAAN' THEN RANG1.CURR_MONTH ELSE 0 END) AS PENERIMAAN_CURR, 
                       SUM (CASE WHEN RANG1.TRXTYPE = 'PENGELUARAN' THEN RANG1.PREV_TOTAL ELSE 0 END) AS PENGELUARAN_PREV, 
                       SUM (CASE WHEN RANG1.TRXTYPE = 'PENGELUARAN' THEN RANG1.CURR_MONTH ELSE 0 END) AS PENGELUARAN_CURR
                FROM (
                    SELECT RANG0.ENTITY_ID AS ENTITY_ID, RANG0.TRXTYPE, SUM(RANG0.PREV_TOTAL) AS PREV_TOTAL, SUM(RANG0.CURR_MONTH) AS CURR_MONTH
                    FROM (
                            SELECT POS1.ENTITY_ID, POS1.TRXTYPE,
                                   (
                                        SELECT SUM(TRX.AMOUNT)
                                        FROM YARDIP_TRANSAKSI AS TRX
                                        WHERE TRX.BUSINESSENTITY_ID = POS1.ENTITY_ID       
                                        AND TRX.TRXDATE >= ? AND TRX.TRXDATE <= ?
                                        AND TRX.TRXTYPE_ID = POS1.ID
                                   ) AS PREV_TOTAL, 
                                   TRX1.AMOUNT AS CURR_MONTH
                            FROM
                                 (
                                    SELECT POS0.ENTITY_ID, POS0.ID, POS0.TRXTYPE, POS0.URAIAN
                                    FROM YARDIP_POSTRANSAKSI AS POS0
                                    WHERE (POS0.TRXTYPE = 'PENERIMAAN' OR POS0.TRXTYPE = 'PENGELUARAN')
                                    AND POS0.THRUDATE >= ? AND POS0.FROMDATE <= ?
                                 ) AS POS1
                            LEFT JOIN
                                 (
                                    SELECT TRX0.BUSINESSENTITY_ID, TRX0.TRXTYPE_ID, SUM(TRX0.AMOUNT) AS AMOUNT
                                    FROM YARDIP_TRANSAKSI AS TRX0
                                    WHERE TRX0.TRXDATE >= ? AND TRX0.TRXDATE <= ?
                                    GROUP BY TRX0.BUSINESSENTITY_ID, TRX0.TRXTYPE_ID
                                 ) AS TRX1
                            ON POS1.ENTITY_ID = TRX1.BUSINESSENTITY_ID AND POS1.ID = TRX1.TRXTYPE_ID
                    ) RANG0
                    GROUP BY RANG0.ENTITY_ID, RANG0.TRXTYPE
                ) AS RANG1
                GROUP BY RANG1.ENTITY_ID
        ) AS RANG2
        JOIN PARTY_PARTYROLE AS ROL0
        ON RANG2.ENTITY_ID = ROL0.ID
    ) AS RANG3
    JOIN PARTY_ORGANIZATION AS ORG0
    ON RANG3.PARTY_ID = ORG0.ID
    ORDER BY ORG0.ID;
    """;

    @Inject
    private EntityManager em;

    public RekapitulasiLaporanFacade() {
        super(RekapitulasiLaporan.class);
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
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern(Constants.KEYFORMAT_LOCALDATE_DEFAULT);

        int year = (Integer) parameters.get("year");
        String yearBegin = fmt.format(LocalDate.now().withDayOfMonth(1).withMonth(1).withYear(year));

        q.setParameter(1, yearBegin);
        q.setParameter(2, yearBegin);

        AccountingPeriod currentPeriod = (AccountingPeriod) parameters.get("period");

        String strCurrentFromDate = fmt.format(currentPeriod.getFromDate());
        String strCurrentThruDate = fmt.format(currentPeriod.getThruDate());
        
        AccountingPeriod previousPeriod = (AccountingPeriod) parameters.get("previousPeriod");
        String strPrevThruDateDate = fmt.format(previousPeriod.getThruDate());

        q.setParameter(3, strPrevThruDateDate);
        
        q.setParameter(4, strCurrentFromDate);
        q.setParameter(5, strCurrentThruDate);
        q.setParameter(6, strCurrentFromDate);
        q.setParameter(7, strCurrentThruDate);

    }

}
