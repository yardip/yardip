/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.party.view.widget;

import id.my.mdn.kupu.core.base.dao.AbstractFacade;
import id.my.mdn.kupu.core.base.view.widget.AbstractMutablePagedValueList;
import id.my.mdn.kupu.core.base.util.FilterTypes.FilterData;
import id.my.mdn.kupu.core.party.dao.ElectronicAddressPurposeTypeFacade;
import id.my.mdn.kupu.core.party.entity.ElectronicAddressPurposeType;
import java.util.List;
import java.util.Map;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;

/**
 *
 * @author aphasan
 */
@Dependent
public class ElectronicAddressPurposeTypeList
        extends AbstractMutablePagedValueList<ElectronicAddressPurposeType> {
    
    @Inject
    private ElectronicAddressPurposeTypeFacade dao;

    @Override
    protected List<ElectronicAddressPurposeType> getPagedFetchedItemsInternal(int first, int pageSize, Map<String, Object> parameters, List<FilterData> filters, List<SorterData> sorters, DefaultList<ElectronicAddressPurposeType> defaultList, AbstractFacade.DefaultChecker defaultChecker) {
        
        return dao.findAll(first, pageSize,
                filters, sorters);
    }

    @Override
    protected long getItemsCountInternal(Map<String, Object> parameters, List<FilterData> filters, DefaultCount defaultCount, AbstractFacade.DefaultChecker defaultChecker) {
        
        return dao.countAll(parameters, filters);
    }

    @Override
    protected void createInternal(ElectronicAddressPurposeType entity) {
        dao.create(entity);
    }

    @Override
    public void edit(ElectronicAddressPurposeType entity) {
        dao.edit(entity);
    }

    @Override
    protected void deleteInternal(ElectronicAddressPurposeType entity) {
        dao.remove(entity);
    }

}
