/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.party.view.converter;

import id.my.mdn.kupu.core.party.dao.ElectronicAddressPurposeTypeFacade;
import id.my.mdn.kupu.core.party.entity.ElectronicAddressPurposeType;
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
@Singleton 
@FacesConverter(value = "ElectronicAddressPurposeTypeConverter", managed = true)
public class ElectronicAddressPurposeTypeConverter implements Converter<ElectronicAddressPurposeType> {
    
    @Inject
    private ElectronicAddressPurposeTypeFacade service;

    @Override
    public ElectronicAddressPurposeType getAsObject(FacesContext context, UIComponent component, String value) {
       return service.find(value);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, ElectronicAddressPurposeType value) {
        return value.getId();
    }
    
}
