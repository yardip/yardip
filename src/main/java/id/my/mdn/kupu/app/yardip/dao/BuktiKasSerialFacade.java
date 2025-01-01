/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB40/SingletonEjbClass.java to edit this template
 */
package id.my.mdn.kupu.app.yardip.dao;

import id.my.mdn.kupu.app.yardip.model.BuktiKasSerial;
import id.my.mdn.kupu.app.yardip.model.BuktiKasSerialId;
import id.my.mdn.kupu.app.yardip.model.Transaksi;
import id.my.mdn.kupu.core.accounting.dao.AccountingPeriodFacade;
import id.my.mdn.kupu.core.accounting.entity.AccountingPeriod;
import id.my.mdn.kupu.core.accounting.entity.AccountingPeriodId;
import id.my.mdn.kupu.core.base.dao.AbstractFacade;
import id.my.mdn.kupu.core.party.entity.BusinessEntity;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Lock;
import jakarta.ejb.LockType;
import jakarta.ejb.Singleton;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Singleton
@LocalBean
public class BuktiKasSerialFacade extends AbstractFacade<BuktiKasSerial> {
    
    @Inject
    private EntityManager em;
    
    @Inject
    private AccountingPeriodFacade periodFacade;

    public BuktiKasSerialFacade() {
        super(BuktiKasSerial.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public String getSerial(Transaksi transaksi) {
        BusinessEntity businessEntity = transaksi.getBusinessEntity();
        LocalDate periodBegin = transaksi.getTrxDate().withDayOfMonth(1);
        LocalDate periodEnd = periodBegin.plusMonths(1).minusDays(1);
        AccountingPeriod period = periodFacade.find(new AccountingPeriodId(businessEntity.getId(), periodBegin, periodEnd));
        
        return getNext(period, businessEntity);
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    @Lock(LockType.WRITE)
    public String getNext(AccountingPeriod period, BusinessEntity entity) {
        BuktiKasSerial buktiKasSerial = find(new BuktiKasSerialId(period.getId(), entity.getId()));
        
        if(buktiKasSerial != null) {
            buktiKasSerial.setSeed(buktiKasSerial.getSeed() + 1);
            edit(buktiKasSerial);
        } else {
            buktiKasSerial = new BuktiKasSerial(period, entity);
            create(buktiKasSerial);
        }
        
        getEntityManager().flush();
        
        return String.valueOf(buktiKasSerial.getSeed());
    }

    
}
