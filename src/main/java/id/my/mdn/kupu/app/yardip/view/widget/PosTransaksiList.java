/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.yardip.view.widget;

import id.my.mdn.kupu.app.yardip.dao.PosTransaksiFacade;
import id.my.mdn.kupu.app.yardip.entity.PosTransaksi;
import id.my.mdn.kupu.core.base.dao.AbstractFacade;
import id.my.mdn.kupu.core.base.view.widget.AbstractMutablePagedValueList;
import id.my.mdn.kupu.core.base.util.FilterTypes.FilterData;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Named(value = "posTransaksiList")
@Dependent
public class PosTransaksiList extends AbstractMutablePagedValueList<PosTransaksi> {
    
    @Inject
    private PosTransaksiFacade dao;

    @Override
    protected List<PosTransaksi> getPagedFetchedItemsInternal(int first, int pageSize, Map<String, Object> parameters, List<FilterData> filters, List<SorterData> sorters, DefaultList<PosTransaksi> defaultList, AbstractFacade.DefaultChecker defaultChecker) {
        return dao.findAll(first, pageSize, parameters, filters, sorters);
    }

    @Override
    protected long getItemsCountInternal(Map<String, Object> parameters, List<FilterData> filters, DefaultCount defaultCount, AbstractFacade.DefaultChecker defaultChecker) {
        return dao.countAll(parameters, filters);
    }

    @Override
    protected void createInternal(PosTransaksi entity) {
        dao.create(entity);
    }

    @Override
    protected void deleteInternal(PosTransaksi entity) {
        dao.remove(entity);
    }

    @Override
    public void edit(PosTransaksi entity) {
        dao.edit(entity);
    }

    
    
}
