/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.core.security.view.widget;

import id.my.mdn.kupu.core.base.dao.AbstractFacade;
import id.my.mdn.kupu.core.base.dao.AbstractFacade.DefaultChecker;
import id.my.mdn.kupu.core.base.view.widget.AbstractPagedValueList;
import id.my.mdn.kupu.core.base.util.FilterTypes.FilterData;
import id.my.mdn.kupu.core.security.dao.GroupAccessControlFacade;
import id.my.mdn.kupu.core.security.model.AccessControl;
import id.my.mdn.kupu.core.security.model.ApplicationSecurityGroup;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Named(value = "groupAccessControlList")
@Dependent
public class GroupAccessControlList extends AbstractPagedValueList<AccessControl> {
    
    @Inject
    private GroupAccessControlFacade dao;

    @Override
    protected List<AccessControl> getPagedFetchedItemsInternal(int first, int pageSize, Map<String, Object> parameters, List<FilterData> filters, List<SorterData> sorters, DefaultList<AccessControl> defaultList, AbstractFacade.DefaultChecker defaultChecker) {
        return dao.findAll(first, pageSize, parameters, filters, sorters, defaultList.get(), defaultChecker);
    }

    @Override
    protected long getItemsCountInternal(Map<String, Object> parameters, List<FilterData> filters, DefaultCount defaultCount, DefaultChecker defaultChecker) {
        return dao.countAll(parameters, filters, defaultCount.get(), defaultChecker);
    }

    public void toggleActivation(AccessControl acl, ApplicationSecurityGroup group) {
        if (acl.isActive()) {
            dao.removeAccess(group, acl);
        } else {
            dao.addAccess(group, acl);
        }
        invalidate();
    }

    
    
}
