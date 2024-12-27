/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.core.accounting.view.widget;

import id.my.mdn.kupu.core.accounting.dao.AccountingPeriodFacade;
import id.my.mdn.kupu.core.accounting.entity.AccountingPeriod;
import id.my.mdn.kupu.core.base.dao.AbstractFacade.DefaultChecker;
import id.my.mdn.kupu.core.base.util.FilterTypes.FilterData;
import id.my.mdn.kupu.core.base.view.widget.AbstractValueList;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Named(value = "accountingPeriodAltList")
@Dependent
public class AccountingPeriodList extends AbstractValueList<AccountingPeriod> {
    
    @Inject
    private AccountingPeriodFacade dao;

    @Override
    protected List<AccountingPeriod> getFetchedItemsInternal(
            Map<String, Object> parameters, List<FilterData> filters, 
            List<SorterData> sorters, DefaultList<AccountingPeriod> defaultList, 
            DefaultChecker defaultChecker) {
        return dao.findAll(filters, sorters);
    }
    
    
}
