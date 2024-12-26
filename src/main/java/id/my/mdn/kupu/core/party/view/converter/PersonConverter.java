/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.party.view.converter;

import id.my.mdn.kupu.core.party.dao.PersonFacade;
import id.my.mdn.kupu.core.party.entity.Person;
import jakarta.enterprise.context.Dependent;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import id.my.mdn.kupu.core.common.util.K.KLong;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 *
 * @author aphasan
 */
@Named("PersonConverter")
@Dependent @FacesConverter(value = "PersonConverter", managed = true)
public class PersonConverter implements Converter<Person> {
    
    @Inject
    private PersonFacade service;

    @Override
    public Person getAsObject(FacesContext context, UIComponent component, String value) {
        Person find = service.find(KLong.valueOf(value));
        return find;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Person value) {
        return value != null ? value.getId().toString() : null;
    }
    
}
