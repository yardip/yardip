/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.yardip.dao;

import id.my.mdn.kupu.app.yardip.model.JenisTransaksi;
import id.my.mdn.kupu.app.yardip.model.ProgresProgramKerja;
import id.my.mdn.kupu.core.accounting.entity.AccountingPeriod;
import id.my.mdn.kupu.core.base.dao.AbstractSqlFacade;
import id.my.mdn.kupu.core.base.util.Constants;
import id.my.mdn.kupu.core.party.entity.BusinessEntity;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.time.format.DateTimeFormatter;
import java.util.Map;

/**
 *
 * @author aphasan
 */
@Stateless
public class ProgresProgramKerjaFacade extends AbstractSqlFacade<ProgresProgramKerja> {

    private static final String FIND_ALL = """
                                   SELECT POS1.URAIAN, 
                                          (SELECT SUM(TRX.AMOUNT)
                                           FROM YARDIP_TRANSAKSI AS TRX
                                           WHERE TRX.BUSINESSENTITY_ID = ?        
                                           AND TRX.TRXDATE >= ? AND TRX.TRXDATE <= ?
                                           AND TRX.TRXTYPE_ID = POS1.ID
                                           ) AS PREV_TOTAL, 
                                          TRX1.AMOUNT AS CURR_MONTH
                                   FROM
                                        (SELECT TRX0.TRXTYPE_ID, SUM(TRX0.AMOUNT) AS AMOUNT
                                         FROM YARDIP_TRANSAKSI AS TRX0
                                         WHERE TRX0.BUSINESSENTITY_ID = ?
                                         AND TRX0.TRXDATE >= ? AND TRX0.TRXDATE <= ?
                                         GROUP BY TRX0.TRXTYPE_ID) AS TRX1
                                   RIGHT OUTER JOIN
                                        (SELECT POS0.ID, POS0.TRXTYPE, POS0.URAIAN
                                         FROM YARDIP_POSTRANSAKSI AS POS0
                                         WHERE POS0.ENTITY_ID = ?
                                         AND POS0.TRXTYPE = ?
                                         AND POS0.THRUDATE >= ? AND POS0.FROMDATE <= ?) AS POS1
                                   ON TRX1.TRXTYPE_ID = POS1.ID
                                   ORDER BY POS1.ID
                              """;
    
    @Inject
    private EntityManager em;

    public ProgresProgramKerjaFacade() {
        super(ProgresProgramKerja.class);
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
        q.setParameter(7, businessEntity.getId());    
        
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern(Constants.KEYFORMAT_LOCALDATE_DEFAULT);
        
        AccountingPeriod currentPeriod = (AccountingPeriod) parameters.get("period");
        
        String strCurrentFromDate = fmt.format(currentPeriod.getFromDate());         
        String strCurrentThruDate = fmt.format(currentPeriod.getThruDate()); 
        
        q.setParameter(5, strCurrentFromDate);
        q.setParameter(6, strCurrentThruDate);
        q.setParameter(9, strCurrentFromDate);
        q.setParameter(10, strCurrentThruDate);  
        
        String strFirstDateOfYear = fmt.format(currentPeriod.getFromDate().withMonth(1).withDayOfMonth(1));         
        String strPreviousThruDate = fmt.format(currentPeriod.getFromDate().withDayOfMonth(1).minusDays(1)); 
        
        q.setParameter(2, strFirstDateOfYear);
        q.setParameter(3, strPreviousThruDate);
        
        JenisTransaksi jenisTransaksi = (JenisTransaksi) parameters.get("jenisTransaksi");
        q.setParameter(8, jenisTransaksi.name());
        
    }

}
