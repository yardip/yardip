/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.yardip.view.widget;

import id.my.mdn.kupu.core.base.dao.AbstractFacade.DefaultChecker;
import id.my.mdn.kupu.core.base.util.FilterTypes.FilterData;
import id.my.mdn.kupu.core.base.view.widget.AbstractValueList;
import id.my.mdn.kupu.core.party.dao.BusinessEntityFacade;
import id.my.mdn.kupu.core.party.entity.BusinessEntity;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Named(value = "entitasList")
@Dependent
public class EntitasList extends AbstractValueList<BusinessEntity> {

    @Inject
    private BusinessEntityFacade dao;

    @Override
    protected List<BusinessEntity> getFetchedItemsInternal(
            Map<String, Object> parameters, List<FilterData> filters, 
            List<SorterData> sorters, DefaultList<BusinessEntity> defaultList, 
            DefaultChecker defaultChecker) {
        
        return dao.findAll();
        
    }
    
    
    
}
