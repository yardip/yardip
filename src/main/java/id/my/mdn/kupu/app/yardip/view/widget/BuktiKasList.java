/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.yardip.view.widget;

import id.my.mdn.kupu.app.yardip.dao.BuktiKasFacade;
import id.my.mdn.kupu.app.yardip.entity.BuktiKas;
import id.my.mdn.kupu.core.base.dao.AbstractFacade.DefaultChecker;
import id.my.mdn.kupu.core.base.view.widget.AbstractMutablePagedValueList;
import id.my.mdn.kupu.core.base.view.widget.AbstractValueList.DefaultList;
import id.my.mdn.kupu.core.base.util.FilterTypes.FilterData;
import id.my.mdn.kupu.core.base.view.widget.IValueList.SorterData;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Named(value = "buktiKasList")
@Dependent
public class BuktiKasList extends AbstractMutablePagedValueList<BuktiKas> {

    @Inject
    private BuktiKasFacade dao;

    @Override
    protected List<BuktiKas> getPagedFetchedItemsInternal(
            int first, int pageSize, Map<String, Object> parameters, 
            List<FilterData> filters, List<SorterData> sorters, 
            DefaultList<BuktiKas> defaultList, DefaultChecker defaultChecker) {
        return dao.findAll(first, pageSize, parameters, 
                filters, sorters, defaultList.get(), defaultChecker);
    }

    @Override
    protected long getItemsCountInternal(Map<String, Object> parameters, 
            List<FilterData> filters, DefaultCount defaultCount, 
            DefaultChecker defaultChecker) {
        return dao.countAll(parameters, filters, defaultCount.get(),
                defaultChecker);
    }

    @Override
    protected void createInternal(BuktiKas entity) {
        dao.create(entity);
    }

    @Override
    protected void deleteInternal(BuktiKas entity) {
        dao.remove(entity);
    }

    @Override
    public void edit(BuktiKas entity) {
        dao.edit(entity);
    }
    
}
