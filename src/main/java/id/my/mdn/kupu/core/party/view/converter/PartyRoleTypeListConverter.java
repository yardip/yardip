/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.party.view.converter;

import id.my.mdn.kupu.core.base.view.converter.SelectionsConverter;
import id.my.mdn.kupu.core.party.dao.PartyRoleTypeFacade;
import id.my.mdn.kupu.core.party.entity.PartyRoleType;
import jakarta.enterprise.context.Dependent;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 *
 * @author aphasan
 */
@Named(value = "PartyRoleTypeListConverter")
@Dependent @FacesConverter(value = "PartyRoleTypeListConverter", managed = true)
public class PartyRoleTypeListConverter extends SelectionsConverter<PartyRoleType> {
    
    @Inject
    private PartyRoleTypeFacade service;

    @Override
    public PartyRoleType getAsObject(String value) {
        return service.find(value);
    }

    @Override
    public String getAsString(PartyRoleType value) {
        return value != null ? value.getId() : null;
    }
    
}
