/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.security.view.converter;

import id.my.mdn.kupu.core.security.dao.ApplicationUserFacade;
import id.my.mdn.kupu.core.security.model.ApplicationUser;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import id.my.mdn.kupu.core.common.util.K.KLong;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

/**
 *
 * @author aphasan
 */
@Singleton
@FacesConverter(value = "ApplicationUserConverter", managed = true)
public class ApplicationUserConverter implements Converter<ApplicationUser> {

    @Inject
    private ApplicationUserFacade dao;

    @Override
    public ApplicationUser getAsObject(FacesContext context, UIComponent component, String value) {
        return dao.find(KLong.valueOf(value));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, ApplicationUser obj) {
        return obj != null ? obj.toString() : null;
    }

}
