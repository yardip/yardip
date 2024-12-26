/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.party.view.converter;

import id.my.mdn.kupu.core.common.util.K.KLong;
import id.my.mdn.kupu.core.party.dao.PersonTypeFacade;
import id.my.mdn.kupu.core.party.entity.PersonType;
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
@Named("PersonTypeConverter")
@Dependent @FacesConverter(value = "PersonTypeConverter", managed = true)
public class PersonTypeConverter implements Converter<PersonType> {

    @Inject
    private PersonTypeFacade service;

    @Override
    public PersonType getAsObject(FacesContext fc, UIComponent uic, String string) {
        return service.find(KLong.valueOf(string));
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, PersonType t) {
        return String.valueOf(t);
    }

}
