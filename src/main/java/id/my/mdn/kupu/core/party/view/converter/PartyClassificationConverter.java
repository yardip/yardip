/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.party.view.converter;

import id.my.mdn.kupu.core.base.util.EntityUtil;
import id.my.mdn.kupu.core.party.dao.PartyClassificationFacade;
import id.my.mdn.kupu.core.party.entity.PartyClassification;
import id.my.mdn.kupu.core.party.entity.PartyClassificationId;
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
@Named("PartyClassificationConverter")
@Dependent @FacesConverter(value = "PartyClassificationConverter", managed = true)
public class PartyClassificationConverter implements Converter<PartyClassification>{
    
    @Inject
    private PartyClassificationFacade classificationFacade;

    @Override
    public PartyClassification getAsObject(FacesContext context, UIComponent component, String value) {
        return classificationFacade.find(new PartyClassificationId(EntityUtil.parseCompositeId(value)));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, PartyClassification value) {
        return value != null ? value.toString() : null;
    }
    
}
