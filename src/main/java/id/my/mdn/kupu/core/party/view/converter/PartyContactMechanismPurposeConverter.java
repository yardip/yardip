/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.party.view.converter;

import id.my.mdn.kupu.core.base.util.EntityUtil;
import id.my.mdn.kupu.core.party.dao.PartyContactMechanismPurposeFacade;
import id.my.mdn.kupu.core.party.entity.PartyContactMechanismPurpose;
import jakarta.enterprise.context.Dependent;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 *
 * @author aphasan
 */
@Named("PartyContactMechanismPurposeConverter")
@Dependent @FacesConverter(value = "PartyContactMechanismPurposeConverter", managed = true)
public class PartyContactMechanismPurposeConverter implements Converter<PartyContactMechanismPurpose> {
    
    @Inject
    private PartyContactMechanismPurposeFacade contactMechanismPurposeFacade;

    @Override
    public PartyContactMechanismPurpose getAsObject(FacesContext context, UIComponent component, String value) {
        return contactMechanismPurposeFacade.find(EntityUtil.parseCompositeId(value));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, PartyContactMechanismPurpose value) {
        return String.valueOf(value);
    }
    
}
