/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.party.view.converter;

import id.my.mdn.kupu.core.party.dao.RegionFacade;
import id.my.mdn.kupu.core.party.entity.Region;
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
@Named("RegionConverter")
@Dependent @FacesConverter(value = "RegionConverter", managed = true)
public class RegionConverter implements Converter<Region> {
    
    @Inject
    private RegionFacade service;

    @Override
    public Region getAsObject(FacesContext context, UIComponent component, String value) {
        return service.find(KLong.valueOf(value));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Region value) {
        return value != null ? String.valueOf(value.getId()) : null;
    }
    
}
