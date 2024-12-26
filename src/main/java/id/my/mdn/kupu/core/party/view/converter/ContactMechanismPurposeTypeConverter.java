/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.party.view.converter;

import id.my.mdn.kupu.core.party.dao.ContactMechanismPurposeTypeFacade;
import id.my.mdn.kupu.core.party.entity.ContactMechanismPurposeType;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

/**
 *
 * @author aphasan
 */
@Singleton 
@FacesConverter(value = "ContactMechanismPurposeTypeConverter", managed = true)
public class ContactMechanismPurposeTypeConverter implements Converter<ContactMechanismPurposeType> {
    
    @Inject
    private ContactMechanismPurposeTypeFacade service;

    @Override
    public ContactMechanismPurposeType getAsObject(FacesContext context, UIComponent component, String value) {
        return service.find(value);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, ContactMechanismPurposeType value) {
        return value != null ? value.toString() : null;
    }
    
}
