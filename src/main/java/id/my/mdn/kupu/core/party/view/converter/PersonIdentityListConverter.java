/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.party.view.converter;

import id.my.mdn.kupu.core.base.util.EntityUtil;
import id.my.mdn.kupu.core.base.view.converter.SelectionsConverter;
import id.my.mdn.kupu.core.party.dao.PersonIdentityFacade;
import id.my.mdn.kupu.core.party.entity.PersonIdentity;
import id.my.mdn.kupu.core.party.entity.PersonIdentityId;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

/**
 *
 * @author aphasan
 */
@Singleton @FacesConverter(value = "PersonIdentityListConverter", managed = true)
public class PersonIdentityListConverter extends SelectionsConverter<PersonIdentity> {
    
    @Inject
    private PersonIdentityFacade service;

    @Override
    public PersonIdentity getAsObject(String value) {
        return service.find(new PersonIdentityId(EntityUtil.parseCompositeId(value)));
    }

    @Override
    public String getAsString(PersonIdentity value) {
        return value.toString();
    }
    
}
