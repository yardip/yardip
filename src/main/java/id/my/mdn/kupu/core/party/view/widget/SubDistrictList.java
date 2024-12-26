/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.party.view.widget;

import id.my.mdn.kupu.core.base.util.FilterTypes.FilterData;
import id.my.mdn.kupu.core.base.view.widget.IValueList;
import id.my.mdn.kupu.core.party.dao.SubDistrictFacade;
import id.my.mdn.kupu.core.party.entity.District;
import id.my.mdn.kupu.core.party.entity.SubDistrict;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author aphasan
 */
@Named(value = "subDistrictList")
@Dependent
public class SubDistrictList implements IValueList<SubDistrict> {
    
    @Inject
    private SubDistrictFacade dao;
    
    private District parent;

    @Override
    public List<SubDistrict> getFetchedItems() {
        return dao.findAll(getFilters());    
    }

    public List<FilterData> getFilters() {
        List<FilterData> filters = new ArrayList<>();
        filters.add(new FilterData("parent", getParent()));
        return filters;
    }

    public District getParent() {
        return parent;
    }

    public void setParent(District parent) {
        this.parent = parent;
    }
    
}
