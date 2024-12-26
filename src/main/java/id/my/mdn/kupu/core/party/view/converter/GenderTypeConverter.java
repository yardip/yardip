/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.party.view.converter;

import id.my.mdn.kupu.core.party.entity.GenderType;
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
@FacesConverter(value = "GenderTypeConverter", managed = true)
public class GenderTypeConverter implements Converter<GenderType>{

    @Override
    public GenderType getAsObject(FacesContext context, UIComponent component, String value) {
        return GenderType.valueOf(value);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, GenderType value) {
        return value != null ? value.toString() : null;
    }
    
}
