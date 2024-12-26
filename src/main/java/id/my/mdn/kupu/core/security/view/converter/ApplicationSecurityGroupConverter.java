/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.security.view.converter;

import id.my.mdn.kupu.core.security.dao.ApplicationSecurityGroupFacade;
import id.my.mdn.kupu.core.security.model.ApplicationSecurityGroup;
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
@FacesConverter(value = "ApplicationSecurityGroupConverter", managed = true)
public class ApplicationSecurityGroupConverter implements Converter<ApplicationSecurityGroup> {

    @Inject
    private ApplicationSecurityGroupFacade dao;

    @Override
    public ApplicationSecurityGroup getAsObject(FacesContext context, UIComponent component, String value) {
        return dao.find(KLong.valueOf(value));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, ApplicationSecurityGroup value) {
        return value != null ? value.toString() : null;
    }

}
