/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.party.view.converter;

import id.my.mdn.kupu.core.base.util.EntityUtil;
import id.my.mdn.kupu.core.base.view.converter.SelectionsConverter;
import id.my.mdn.kupu.core.party.dao.PartyClassificationFacade;
import id.my.mdn.kupu.core.party.entity.PartyClassification;
import id.my.mdn.kupu.core.party.entity.PartyClassificationId;
import jakarta.enterprise.context.Dependent;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 *
 * @author aphasan
 */
@Named("PartyClassificationListConverter")
@Dependent @FacesConverter(value = "PartyClassificationListConverter", managed = true)
public class PartyClassificationListConverter extends SelectionsConverter<PartyClassification>{
    
    @Inject
    private PartyClassificationFacade classificationFacade;

    @Override
    public PartyClassification getAsObject(String value) {
        return classificationFacade.find(new PartyClassificationId(EntityUtil.parseCompositeId(value)));
    }

    @Override
    public String getAsString(PartyClassification value) {
        return value != null ? value.toString() : null;
    }
    
}
