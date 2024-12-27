/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.core.hr.view.widget;

import id.my.mdn.kupu.core.base.dao.AbstractFacade.DefaultChecker;
import id.my.mdn.kupu.core.base.util.FilterTypes.FilterData;
import id.my.mdn.kupu.core.base.view.widget.AbstractMutablePagedValueList;
import id.my.mdn.kupu.core.hr.dao.EmploymentFacade;
import id.my.mdn.kupu.core.hr.entity.Employment;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Named(value = "employmentList")
@Dependent
public class EmploymentList extends AbstractMutablePagedValueList<Employment> {
    
    @Inject
    private EmploymentFacade dao;
    
    @Inject
    private EmploymentFilter filterContent;

    @PostConstruct
    public void init() {  
        getSorters().add(SorterData.by("fromDate", SorterData.DESC));
        
        getFilter().setContent(filterContent);
    }

    @Override
    protected List<Employment> getPagedFetchedItemsInternal(
            int first, int pageSize, Map<String, Object> parameters, 
            List<FilterData> filters, List<SorterData> sorters, 
            DefaultList<Employment> defaultList, DefaultChecker defaultChecker) {
        return dao.findAll(first, pageSize, 
                parameters, filters, sorters);
    }

    @Override
    protected long getItemsCountInternal(
            Map<String, Object> parameters, List<FilterData> filters, 
            DefaultCount defaultCount, DefaultChecker defaultChecker) {
        return dao.countAll(parameters, filters);
    }

    @Override
    protected void createInternal(Employment entity) {
        dao.create(entity);
    }

    @Override
    public void edit(Employment entity) {
        dao.edit(entity);
    }

    @Override
    protected void deleteInternal(Employment entity) {
        dao.remove(entity);
    }    
    
}
