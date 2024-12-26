/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.party.view.widget;

import id.my.mdn.kupu.core.base.util.FilterTypes.FilterData;
import id.my.mdn.kupu.core.base.view.widget.IValueList;
import id.my.mdn.kupu.core.party.dao.UrbanFacade;
import id.my.mdn.kupu.core.party.entity.SubDistrict;
import id.my.mdn.kupu.core.party.entity.Urban;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author aphasan
 */
@Named(value = "urbanList")
@Dependent
public class UrbanList implements IValueList<Urban> {
    
    @Inject
    private UrbanFacade dao;
    
    private SubDistrict parent;

    @Override
    public List<Urban> getFetchedItems() {
        return dao.findAll(getFilters());    
    }

    public List<FilterData> getFilters() {
        List<FilterData> filters = new ArrayList<>();
        filters.add(new FilterData("parent", getParent()));
        return filters;
    }

    public SubDistrict getParent() {
        return parent;
    }

    public void setParent(SubDistrict parent) {
        this.parent = parent;
    }
    
}
