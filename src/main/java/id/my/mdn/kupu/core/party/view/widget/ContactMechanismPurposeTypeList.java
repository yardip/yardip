/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.party.view.widget;

import id.my.mdn.kupu.core.base.view.widget.IValueList;
import id.my.mdn.kupu.core.party.entity.ContactMechanismPurposeType;
import id.my.mdn.kupu.core.party.dao.ContactMechanismPurposeTypeFacade;
import java.io.Serializable;
import java.util.List;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 *
 * @author aphasan
 */
@Named(value = "contactMechanismPurposeTypeList")
@Dependent
public class ContactMechanismPurposeTypeList 
        implements IValueList<ContactMechanismPurposeType>, Serializable {

    @Inject
    private ContactMechanismPurposeTypeFacade contactMechanismPurposeTypeFacade;

    @Override
    public List<ContactMechanismPurposeType> getFetchedItems() {
        return contactMechanismPurposeTypeFacade.findAll();
    }
    
}
