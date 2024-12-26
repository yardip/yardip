/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.party.view.converter;

import id.my.mdn.kupu.core.party.dao.PartyRoleTypeFacade;
import id.my.mdn.kupu.core.party.entity.PartyRoleType;
import jakarta.enterprise.context.Dependent;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 *
 * @author aphasan
 */
@Named(value = "PartyRoleTypeConverter")
@Dependent @FacesConverter(value = "PartyRoleTypeConverter", managed = true)
public class PartyRoleTypeConverter implements Converter<PartyRoleType> {
    
    @Inject
    private PartyRoleTypeFacade service;

    @Override
    public PartyRoleType getAsObject(FacesContext context, UIComponent component, String value) {
        return service.find(value);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, PartyRoleType value) {
        return value != null ? value.getId() : null;
    }
    
}
