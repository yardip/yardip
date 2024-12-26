/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.party.view.converter;

import id.my.mdn.kupu.core.party.entity.MaritalStatusType;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Singleton;

/**
 *
 * @author aphasan
 */
@Singleton 
@FacesConverter(value = "MaritalStatusTypeConverter", managed = true)
public class MaritalStatusTypeConverter implements Converter<MaritalStatusType> {

    @Override
    public MaritalStatusType getAsObject(FacesContext context, UIComponent component, String value) {
        return MaritalStatusType.valueOf(value);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, MaritalStatusType value) {
        String ret = value != null ? value.toString() : null;
        return ret;
    }
    
}
