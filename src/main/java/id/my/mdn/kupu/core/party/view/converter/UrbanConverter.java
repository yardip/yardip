/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.party.view.converter;

import id.my.mdn.kupu.core.party.dao.UrbanFacade;
import id.my.mdn.kupu.core.party.entity.Urban;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import id.my.mdn.kupu.core.common.util.K.KLong;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.enterprise.context.Dependent;

/**
 *
 * @author aphasan
 */
@Named("UrbanConverter")
@Dependent @FacesConverter(value = "UrbanConverter", managed = true)
public class UrbanConverter implements Converter<Urban> {
    
    @Inject
    private UrbanFacade service; 

    @Override
    public Urban getAsObject(FacesContext context, UIComponent component, String value) {
        return service.find(KLong.valueOf(value));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Urban value) {
        return value != null ? String.valueOf(value.getId()) : null;
    }
    
}
