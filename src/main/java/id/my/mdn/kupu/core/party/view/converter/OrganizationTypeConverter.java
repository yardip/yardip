/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.party.view.converter;

import id.my.mdn.kupu.core.common.util.K.KLong;
import id.my.mdn.kupu.core.party.dao.OrganizationTypeFacade;
import id.my.mdn.kupu.core.party.entity.OrganizationType;
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
@Named("OrganizationTypeConverter")
@Dependent @FacesConverter(value = "OrganizationTypeConverter", managed = true)
public class OrganizationTypeConverter implements Converter<OrganizationType> {

    @Inject
    private OrganizationTypeFacade service;

    @Override
    public OrganizationType getAsObject(FacesContext fc, UIComponent uic, String string) {
        return service.find(KLong.valueOf(string));
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, OrganizationType t) {
        return String.valueOf(t);
    }

}
