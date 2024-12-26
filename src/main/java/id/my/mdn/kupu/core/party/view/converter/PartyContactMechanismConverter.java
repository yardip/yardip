/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.party.view.converter;

import id.my.mdn.kupu.core.base.util.EntityUtil;
import id.my.mdn.kupu.core.party.dao.PartyContactMechanismFacade;
import id.my.mdn.kupu.core.party.entity.PartyContactMechanism;
import id.my.mdn.kupu.core.party.entity.PartyContactMechanismId;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

/**
 *
 * @author aphasan
 */
@Singleton @FacesConverter(value = "PartyContactMechanismConverter", managed = true)
public class PartyContactMechanismConverter implements Converter<PartyContactMechanism> {
    
    @Inject
    private PartyContactMechanismFacade contactMechanismFacade;

    @Override
    public PartyContactMechanism getAsObject(FacesContext context, UIComponent component, String value) {
        return contactMechanismFacade.find(new PartyContactMechanismId(EntityUtil.parseCompositeId(value)));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, PartyContactMechanism value) {
        return String.valueOf(value);
    }
    
}
