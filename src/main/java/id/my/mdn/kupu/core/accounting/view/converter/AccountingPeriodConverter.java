/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.core.accounting.view.converter;

import id.my.mdn.kupu.core.accounting.dao.AccountingPeriodFacade;
import id.my.mdn.kupu.core.accounting.entity.AccountingPeriod;
import id.my.mdn.kupu.core.accounting.entity.AccountingPeriodId;
import id.my.mdn.kupu.core.base.util.EntityUtil;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Singleton
@FacesConverter(value = "AccountingPeriodConverter", managed = true)
public class AccountingPeriodConverter implements Converter<AccountingPeriod> {
    
    @Inject
    private AccountingPeriodFacade dao;

    @Override
    public AccountingPeriod getAsObject(FacesContext context, UIComponent component, String value) {
        return dao.find(new AccountingPeriodId(EntityUtil.parseCompositeId(value)));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, AccountingPeriod value) {
        return value != null ? String.valueOf(value) : null;
    }

    
    
}
