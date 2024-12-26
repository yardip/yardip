/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.core.reporting.view;

import id.my.mdn.kupu.core.base.dao.AbstractFacade;
import id.my.mdn.kupu.core.base.view.widget.AbstractMutablePagedValueList;
import id.my.mdn.kupu.core.base.view.widget.CommonFilter;
import id.my.mdn.kupu.core.base.util.FilterTypes.FilterData;
import id.my.mdn.kupu.core.reporting.dao.ReportTemplateFacade;
import id.my.mdn.kupu.core.reporting.model.ReportTemplate;
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
@Named(value = "reportTemplateList")
@Dependent
public class ReportTemplateList extends AbstractMutablePagedValueList<ReportTemplate> {
    
    @Inject
    private ReportTemplateFacade dao;  

    @Inject    
    private CommonFilter filterContent;

    @PostConstruct
    public void init() {
        filter.setContent(filterContent);
    }

    @Override
    protected List<ReportTemplate> getPagedFetchedItemsInternal(int first, int pageSize, Map<String, Object> parameters, List<FilterData> filters, List<SorterData> sorters, DefaultList<ReportTemplate> defaultList, AbstractFacade.DefaultChecker defaultChecker) {
        
        return dao.findAll(first, pageSize, parameters, filters, sorters);
    }

    @Override
    protected long getItemsCountInternal(Map<String, Object> parameters, List<FilterData> filters, DefaultCount defaultCount, AbstractFacade.DefaultChecker defaultChecker) {
        
        return dao.countAll(parameters, filters, 0L);
    }

    @Override
    protected void createInternal(ReportTemplate entity) {
        dao.create(entity);
    }

    @Override
    public void edit(ReportTemplate entity) {
        dao.edit(entity);
    }

    @Override
    protected void deleteInternal(ReportTemplate entity) {
        dao.remove(entity);
    }

}
