/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.core.party.view.widget;

import id.my.mdn.kupu.core.party.dao.BusinessEntityFacade;
import id.my.mdn.kupu.core.party.entity.BusinessEntity;
import id.my.mdn.kupu.core.base.dao.AbstractFacade.DefaultChecker;
import id.my.mdn.kupu.core.base.view.widget.AbstractMutableTree;
import id.my.mdn.kupu.core.base.view.widget.AbstractPagedValueList.DefaultCount;
import id.my.mdn.kupu.core.base.util.FilterTypes.FilterData;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Dependent
public class BusinessEntityTree extends AbstractMutableTree<BusinessEntity> implements Serializable {
    
    @Inject
    private BusinessEntityFacade dao;

    @Override
    protected List<BusinessEntity> getFetchedItemsInternal(
            Map<String, Object> parameters, 
            List<FilterData> filters, List<SorterData> sorters, 
            DefaultList<BusinessEntity> defaultReturn, DefaultChecker defaultChecker
    ) {
        return dao.findAll(parameters, filters, sorters);
    }

    @Override
    protected long getItemsCountInternal(
            Map<String, Object> parameters, List<FilterData> filters, 
            DefaultCount defaultCount, DefaultChecker defaultChecker
    ) {
        return dao.countAll(parameters, filters);
    }

    @Override
    protected void createInternal(BusinessEntity entity) {
        dao.create(entity);
    }

    @Override
    protected void deleteInternal(BusinessEntity entity) {
        dao.remove(entity);
    }

    @Override
    public void edit(BusinessEntity entity) {
        dao.edit(entity);
    }

    
    
}
