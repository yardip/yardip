/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.party.view;

import id.my.mdn.kupu.core.base.view.MultipleChooserPage;
import id.my.mdn.kupu.core.base.view.annotation.Bookmarked;
import id.my.mdn.kupu.core.base.view.converter.SelectionsConverter;
import id.my.mdn.kupu.core.party.entity.OrganizationType;
import id.my.mdn.kupu.core.party.view.converter.OrganizationTypeListConverter;
import id.my.mdn.kupu.core.party.view.widget.OrganizationTypeList;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author aphasan
 */
@Named(value = "organizationTypeChooserPage")
@ViewScoped
public class OrganizationTypeChooserPage extends MultipleChooserPage<OrganizationType> implements Serializable {
    
    @Inject
    @Bookmarked
    private OrganizationTypeList typeList;

    @Override
    protected List<OrganizationType> returns() {
        return typeList.getSelections();
    }

    @Override
    protected Class<? extends SelectionsConverter<OrganizationType>> getConverter() {
        return OrganizationTypeListConverter.class;
    }

    public OrganizationTypeList getTypeList() {
        return typeList;
    }
    
}
