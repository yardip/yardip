/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.party.view.converter;

import id.my.mdn.kupu.core.common.util.K.KLong;
import id.my.mdn.kupu.core.party.dao.PostalCodeFacade;
import id.my.mdn.kupu.core.party.entity.PostalCode;
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
@Named("PostalCodeConverter")
@Dependent @FacesConverter(value = "PostalCodeConverter", managed = true)
public class PostalCodeConverter implements Converter<PostalCode> {

    @Inject
    private PostalCodeFacade postalCodeFacade;

    @Override
    public PostalCode getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        return postalCodeFacade.find(KLong.valueOf(value));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, PostalCode value) {
        return value != null ? String.valueOf(value.getId()) : null;
    }

}
