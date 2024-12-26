/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.party.view.converter;

import id.my.mdn.kupu.core.base.view.converter.SelectionsConverter;
import id.my.mdn.kupu.core.common.util.K.KLong;
import id.my.mdn.kupu.core.party.dao.PersonTypeFacade;
import id.my.mdn.kupu.core.party.entity.PersonType;
import jakarta.enterprise.context.Dependent;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 *
 * @author aphasan
 */
@Named(value = "PersonTypeListConverter")
@Dependent @FacesConverter(value = "PersonTypeListConverter", managed = true)
public class PersonTypeListConverter extends SelectionsConverter<PersonType> {

    @Inject
    private PersonTypeFacade service;

    @Override
    public PersonType getAsObject(String string) {
        return service.find(KLong.valueOf(string));
    }

    @Override
    public String getAsString(PersonType t) {
        return String.valueOf(t);
    }

}
