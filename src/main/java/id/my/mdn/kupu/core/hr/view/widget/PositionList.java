/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.core.hr.view.widget;

import id.my.mdn.kupu.core.base.dao.AbstractFacade.DefaultChecker;
import id.my.mdn.kupu.core.base.util.FilterTypes.FilterData;
import id.my.mdn.kupu.core.base.view.widget.AbstractValueList;
import id.my.mdn.kupu.core.hr.dao.PositionAltFacade;
import id.my.mdn.kupu.core.hr.entity.Position;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Named(value = "positionList")
@Dependent
public class PositionList extends AbstractValueList<Position>{

    @Inject
    private PositionAltFacade dao;

    @Override
    protected List<Position> getFetchedItemsInternal(
            Map<String, Object> parameters, List<FilterData> filters, 
            List<SorterData> sorters, DefaultList<Position> defaultList, DefaultChecker defaultChecker) {
        
        return dao.findAll(filters, sorters);
        
    }
    
}
