/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB40/StatelessEjbClass.java to edit this template
 */
package id.my.mdn.kupu.app.yardip.dao;

import id.my.mdn.kupu.app.yardip.model.PosTransaksi;
import id.my.mdn.kupu.app.yardip.model.ProgramKerja;
import id.my.mdn.kupu.core.accounting.dao.AccountingPeriodFacade;
import id.my.mdn.kupu.core.accounting.entity.AccountingPeriod;
import id.my.mdn.kupu.core.base.dao.AbstractSqlFacade;
import id.my.mdn.kupu.core.base.util.Constants;
import id.my.mdn.kupu.core.base.util.FilterTypes.FilterData;
import id.my.mdn.kupu.core.base.util.Result;
import id.my.mdn.kupu.core.party.entity.BusinessEntity;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.time.format.DateTimeFormatter;
import java.util.Map;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Stateless
public class ProgramKerjaFacade extends AbstractSqlFacade<ProgramKerja> {
    
    @Inject
    private EntityManager em;
    
    @Inject
    private AccountingPeriodFacade periodFacade;
    
    @Inject
    private PosTransaksiFacade posTransaksiFacade;

    private static final String PROGRAMKERJA_QUERY
            = """
            SELECT ID, URAIAN, TARGET, REALISASI, (CASE WHEN TARGET <> 0 THEN ROUND(((REALISASI / TARGET)) * 100) ELSE 100 END) AS CAPAIAN
            FROM (
                SELECT POS1.ID, POS1.TRXTYPE, POS1.URAIAN, POS1.TARGET,
                       (CASE WHEN T1.REALISASI IS NOT NULL THEN T1.REALISASI ELSE 0.00 END) AS REALISASI
                FROM (
                    SELECT POS0.ID, POS0.TRXTYPE, POS0.URAIAN, POS0.TARGET
                    FROM YARDIP_POSTRANSAKSI AS POS0
                    WHERE POS0.ENTITY_ID = ?
                    AND POS0.FROMDATE <= ?
                    AND POS0.THRUDATE >= POS0.FROMDATE
                    AND POS0.THRUDATE >= ?
                ) AS POS1
                LEFT JOIN (
                    SELECT T0.TRXTYPE_ID, SUM(T0.AMOUNT) AS REALISASI
                    FROM YARDIP_TRANSAKSI AS T0
                    WHERE T0.BUSINESSENTITY_ID = ?
                    AND T0.TRXDATE >= ?
                    AND T0.TRXDATE <= ?
                    GROUP BY T0.TRXTYPE_ID
                ) AS T1
                ON POS1.ID = T1.TRXTYPE_ID
            ) AS POS2
            """;
    
    @Override
    protected void setParameters(Query q, Map<String, Object> parameters) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern(Constants.KEYFORMAT_LOCALDATE_DEFAULT);
        AccountingPeriod period = (AccountingPeriod) parameters.get("period");
        q.setParameter(1, ((BusinessEntity) parameters.get("entity")).getId());
        q.setParameter(2, fmt.format(period.getThruDate()));
        q.setParameter(3, fmt.format(period.getFromDate()));
        q.setParameter(4, ((BusinessEntity) parameters.get("entity")).getId());
        q.setParameter(5, fmt.format(periodFacade.getAncestorRoot(period).getFromDate()));
        q.setParameter(6, fmt.format(period.getThruDate()));
    }

    @Override
    protected String applyFilter(FilterData filterData) {
        switch (filterData.name) {
            case "jenisTransaksi":
                return "POS2.TRXTYPE = '" + filterData.value + "'";
            default:
                return null;
        }
    }

    @Override
    protected String translateOrderField(String fieldName) {
        return "POS2." + fieldName.toUpperCase();
    }

    public ProgramKerjaFacade() {
        super(ProgramKerja.class);
    }

    @Override
    protected String getFindAllQuery() {
        return PROGRAMKERJA_QUERY;
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public Result<String> remove(ProgramKerja entity) {
        PosTransaksi posTransaksi = posTransaksiFacade.find(entity.getId());
        if(posTransaksi != null) {
            return posTransaksiFacade.remove(posTransaksi);
        }
        return new Result<>(true, "No Op");
    }
    

}
