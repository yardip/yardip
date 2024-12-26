/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.party.view.converter;

import id.my.mdn.kupu.core.base.util.EntityUtil;
import id.my.mdn.kupu.core.base.view.converter.SelectionsConverter;
import id.my.mdn.kupu.core.party.dao.PartyContactMechanismFacade;
import id.my.mdn.kupu.core.party.entity.PartyContactMechanism;
import id.my.mdn.kupu.core.party.entity.PartyContactMechanismId;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

/**
 *
 * @author aphasan
 */
@Singleton @FacesConverter(value = "PartyContactMechanismListConverter", managed = true)
public class PartyContactMechanismListConverter extends SelectionsConverter<PartyContactMechanism> {
    
    @Inject
    private PartyContactMechanismFacade contactMechanismFacade;

    @Override
    public PartyContactMechanism getAsObject(String value) {
        return contactMechanismFacade.find(new PartyContactMechanismId(EntityUtil.parseCompositeId(value)));
    }

    @Override
    public String getAsString(PartyContactMechanism value) {
        return String.valueOf(value);
    }
    
}
