/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.party.view.converter;

import id.my.mdn.kupu.core.base.view.converter.SelectionsConverter;
import id.my.mdn.kupu.core.party.dao.PersonIdentityTypeFacade;
import id.my.mdn.kupu.core.party.entity.PersonIdentityType;
import jakarta.enterprise.context.Dependent;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 *
 * @author aphasan
 */
@Named("PersonIdentityTypeListConverter")
@Dependent @FacesConverter(value = "PersonIdentityTypeListConverter", managed = true)
public class PersonIdentityTypeListConverter extends SelectionsConverter<PersonIdentityType> {
    
    @Inject
    private PersonIdentityTypeFacade service;

    @Override
    protected PersonIdentityType getAsObject(String value) {
        return service.find(value);
    }

    @Override
    protected String getAsString(PersonIdentityType value) {
        return value != null ? value.getId() : null;
    }
    
}
