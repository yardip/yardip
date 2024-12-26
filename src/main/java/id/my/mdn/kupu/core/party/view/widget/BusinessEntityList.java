/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.core.party.view.widget;

import id.my.mdn.kupu.core.party.dao.BusinessEntityFacade;
import id.my.mdn.kupu.core.party.entity.BusinessEntity;
import id.my.mdn.kupu.core.base.dao.AbstractFacade;
import id.my.mdn.kupu.core.base.view.widget.AbstractValueList;
import id.my.mdn.kupu.core.base.util.FilterTypes.FilterData;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Dependent
public class BusinessEntityList extends AbstractValueList<BusinessEntity>{
    
    @Inject
    private BusinessEntityFacade dao;

    @Override
    protected List<BusinessEntity> getFetchedItemsInternal(Map<String, Object> parameters, List<FilterData> filters, List<SorterData> sorters, DefaultList<BusinessEntity> defaultList, AbstractFacade.DefaultChecker defaultChecker) {
        return dao.findAll();
    }
    
}
