/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.party.view.converter;

import id.my.mdn.kupu.core.base.view.converter.SelectionsConverter;
import id.my.mdn.kupu.core.common.util.K.KLong;
import id.my.mdn.kupu.core.party.dao.PersonFacade;
import id.my.mdn.kupu.core.party.entity.Person;
import jakarta.enterprise.context.Dependent;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 *
 * @author aphasan
 */
@Named(value = "PersonListConverter")
@Dependent @FacesConverter(value = "PersonListConverter", managed = true)
public class PersonListConverter extends SelectionsConverter<Person> {
    
    @Inject
    private PersonFacade service;

    @Override
    protected Person getAsObject(String value) {
        return service.find(KLong.valueOf(value));
    }

    @Override
    protected String getAsString(Person obj) {
        return obj != null ? obj.getId().toString() : null;
    }
    
}
