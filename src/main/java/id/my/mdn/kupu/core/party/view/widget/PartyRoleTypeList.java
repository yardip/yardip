/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.party.view.widget;

import id.my.mdn.kupu.core.base.dao.AbstractFacade;
import id.my.mdn.kupu.core.base.view.widget.AbstractMutablePagedValueList;
import id.my.mdn.kupu.core.base.view.widget.CommonFilter;
import id.my.mdn.kupu.core.base.util.FilterTypes.FilterData;
import id.my.mdn.kupu.core.party.dao.PartyRoleTypeFacade;
import id.my.mdn.kupu.core.party.entity.PartyRoleType;
import java.util.List;
import java.util.Map;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 *
 * @author aphasan
 */
@Named(value = "partyRoleTypeList")
@Dependent
public class PartyRoleTypeList 
        extends AbstractMutablePagedValueList<PartyRoleType> {
    
    @Inject
    private PartyRoleTypeFacade dao;
    
    @Inject
    private CommonFilter filterContent;

    @PostConstruct
    public void init() {        
        filter.setContent(filterContent);
    }

    @Override
    protected List<PartyRoleType> getPagedFetchedItemsInternal(int first, int pageSize, Map<String, Object> parameters, List<FilterData> filters, List<SorterData> sorters, DefaultList<PartyRoleType> defaultList, AbstractFacade.DefaultChecker defaultChecker) {
        return dao.findAll(first, pageSize, parameters, filters, sorters);
    }

    @Override
    protected long getItemsCountInternal(Map<String, Object> parameters, List<FilterData> filters, DefaultCount defaultCount, AbstractFacade.DefaultChecker defaultChecker) {
        return dao.countAll(parameters, filters);
    }

    @Override
    protected void createInternal(PartyRoleType entity) {
        dao.create(entity);
    }

    @Override
    public void edit(PartyRoleType entity) {
        dao.edit(entity);
    }

    @Override
    protected void deleteInternal(PartyRoleType entity) {
        dao.remove(entity);
    }
    
}
