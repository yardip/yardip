/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.party.view.widget;

import id.my.mdn.kupu.core.base.view.widget.IValueList;
import id.my.mdn.kupu.core.party.dao.PersonIdentityTypeFacade;
import id.my.mdn.kupu.core.party.entity.PersonIdentityType;
import java.util.List;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 *
 * @author aphasan
 */
@Named(value = "personIdentityTypeList2")
@Dependent
public class PersonIdentityTypeList2 
        implements IValueList<PersonIdentityType> {
    
    @Inject
    private PersonIdentityTypeFacade service;

    @Override
    public List<PersonIdentityType> getFetchedItems() {
        return service.findAll();
    }

}
