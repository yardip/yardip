/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.core.accounting.view.converter;

import id.my.mdn.kupu.core.accounting.dao.AccountingPeriodFacade;
import id.my.mdn.kupu.core.accounting.entity.AccountingPeriod;
import id.my.mdn.kupu.core.accounting.entity.AccountingPeriodId;
import id.my.mdn.kupu.core.base.util.EntityUtil;
import id.my.mdn.kupu.core.base.view.converter.SelectionsConverter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Singleton
@FacesConverter(value = "AccountingPeriodConverter", managed = true)
public class AccountingPeriodListConverter extends SelectionsConverter<AccountingPeriod> {
    
    @Inject
    private AccountingPeriodFacade dao;

    @Override
    public AccountingPeriod getAsObject(String value) {
        return dao.find(new AccountingPeriodId(EntityUtil.parseCompositeId(value)));
    }

    @Override
    public String getAsString(AccountingPeriod value) {
        return value != null ? String.valueOf(value) : null;
    }

    
    
}
