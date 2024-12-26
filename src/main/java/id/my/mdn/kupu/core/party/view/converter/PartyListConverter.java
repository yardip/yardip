/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.party.view.converter;

import id.my.mdn.kupu.core.base.view.converter.SelectionsConverter;
import id.my.mdn.kupu.core.common.util.K.KLong;
import id.my.mdn.kupu.core.party.dao.PartyFacade;
import id.my.mdn.kupu.core.party.entity.Party;
import jakarta.enterprise.context.Dependent;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 *
 * @author aphasan
 */
@Named(value = "PartyListConverter")
@Dependent @FacesConverter(value = "PartyListConverter", managed = true)
public class PartyListConverter extends SelectionsConverter<Party> {
    
    @Inject
    private PartyFacade service;

    @Override
    protected Party getAsObject(String value) {
        return service.find(KLong.valueOf(value));
    }

    @Override
    protected String getAsString(Party value) {
        return value != null ? String.valueOf(value) : null;
    }
    
}
