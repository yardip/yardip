/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.yardip.view.widget;

import id.my.mdn.kupu.app.yardip.dao.ProgramKerjaFacade;
import id.my.mdn.kupu.app.yardip.entity.ProgramKerja;
import id.my.mdn.kupu.core.base.dao.AbstractFacade.DefaultChecker;
import id.my.mdn.kupu.core.base.view.widget.AbstractMutablePagedValueList;
import id.my.mdn.kupu.core.base.util.FilterTypes.FilterData;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Named(value = "programKerjaList")
@Dependent
public class ProgramKerjaList extends AbstractMutablePagedValueList<ProgramKerja> {

    @Inject
    private ProgramKerjaFacade dao;
    
    @Inject
    private ProgramKerjaFilter filterContent;

    @PostConstruct
    public void init() {
        filter.setContent(filterContent);
        
        getSorters().add(SorterData.by("id"));
        
        setDefaultChecker(this::returnDefault );
        setDefaultList(() -> new ArrayList<>());
        setDefaultCount(() -> 0L);
    }
    
    private boolean returnDefault() {
        return hiddenParameters.get().get("period") == null;
    }

    @Override
    protected List<ProgramKerja> getPagedFetchedItemsInternal(
            int first, int pageSize, Map<String, Object> parameters,
            List<FilterData> filters, List<SorterData> sorters,
            DefaultList<ProgramKerja> defaultList, DefaultChecker defaultChecker) {
        return dao.findAll(first, pageSize,
                parameters, filters, sorters, defaultList.get(), defaultChecker);
    }

    @Override
    protected long getItemsCountInternal(
            Map<String, Object> parameters, List<FilterData> filters,
            DefaultCount defaultCount, DefaultChecker defaultChecker) {
        return dao.countAll(parameters, filters, defaultCount.get(),
                defaultChecker);
    }

    @Override
    protected void createInternal(ProgramKerja entity) {
        
    }

    @Override
    public void edit(ProgramKerja entity) {
        
    }

    @Override
    protected void deleteInternal(ProgramKerja entity) {
        dao.remove(entity);
    }

}
