/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.party.view.converter;

import id.my.mdn.kupu.core.party.dao.PostalAddressPurposeTypeFacade;
import id.my.mdn.kupu.core.party.entity.PostalAddressPurposeType;
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
@FacesConverter(value = "PostalAddressPurposeTypeConverter", managed = true)
public class PostalAddressPurposeTypeConverter implements Converter<PostalAddressPurposeType> {
    
    @Inject
    private PostalAddressPurposeTypeFacade service;

    @Override
    public PostalAddressPurposeType getAsObject(FacesContext context, UIComponent component, String value) {
       return service.find(value);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, PostalAddressPurposeType value) {
        return value.getId();
    }
    
}
