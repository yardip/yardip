/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.party.view.converter;

import id.my.mdn.kupu.core.party.dao.SubDistrictFacade;
import id.my.mdn.kupu.core.party.entity.SubDistrict;
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
@Named("SubDistrictConverter")
@Dependent @FacesConverter(value = "SubDistrictConverter", managed = true)
public class SubDistrictConverter implements Converter<SubDistrict> {
    
    @Inject
    private SubDistrictFacade service; 

    @Override
    public SubDistrict getAsObject(FacesContext context, UIComponent component, String value) {
        return service.find(KLong.valueOf(value));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, SubDistrict value) {
        return value != null ? String.valueOf(value.getId()) : null;
    }
    
}
