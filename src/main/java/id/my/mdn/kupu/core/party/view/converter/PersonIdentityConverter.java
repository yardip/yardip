/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.party.view.converter;

import id.my.mdn.kupu.core.base.util.EntityUtil;
import id.my.mdn.kupu.core.party.dao.PersonIdentityFacade;
import id.my.mdn.kupu.core.party.entity.PersonIdentity;
import id.my.mdn.kupu.core.party.entity.PersonIdentityId;
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
@FacesConverter(value = "PersonIdentityConverter", managed = true)
public class PersonIdentityConverter implements Converter<PersonIdentity> {
    
    @Inject
    private PersonIdentityFacade service;

    @Override
    public PersonIdentity getAsObject(FacesContext context, UIComponent component, String value) {
        return service.find(new PersonIdentityId(EntityUtil.parseCompositeId(value)));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, PersonIdentity value) {
        return value.toString();
    }
    
}
