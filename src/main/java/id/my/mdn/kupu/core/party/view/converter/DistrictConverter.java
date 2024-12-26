/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.party.view.converter;

import id.my.mdn.kupu.core.common.util.K.KLong;
import id.my.mdn.kupu.core.party.dao.DistrictFacade;
import id.my.mdn.kupu.core.party.entity.District;
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
@Named("DistrictConverter")
@Dependent @FacesConverter(value = "DistrictConverter", managed = true)
public class DistrictConverter implements Converter<District> {
    
    @Inject
    private DistrictFacade service;

    @Override
    public District getAsObject(FacesContext context, UIComponent component, String value) {
        return service.find(KLong.valueOf(value));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, District value) {
        return value != null ? String.valueOf(value.getId()) : null;
    }
    
}
