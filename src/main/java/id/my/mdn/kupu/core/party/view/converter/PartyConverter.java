/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.party.view.converter;

import id.my.mdn.kupu.core.party.dao.PartyFacade;
import id.my.mdn.kupu.core.party.entity.Party;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import id.my.mdn.kupu.core.common.util.K.KLong;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

/**
 *
 * @author aphasan
 */
@Singleton
@FacesConverter(value = "PartyConverter", managed = true)
public class PartyConverter implements Converter<Party> {
    
    @Inject
    private PartyFacade service;

    @Override
    public Party getAsObject(FacesContext context, UIComponent component, String value) {
        Party org = service.find(KLong.valueOf(value));
        
        return org;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Party value) {
        return value != null ? String.valueOf(value) : null;
    }
    
}
