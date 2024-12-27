/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.core.hr.view.converter;

import id.my.mdn.kupu.core.common.util.K.KLong;
import id.my.mdn.kupu.core.hr.dao.PositionFacade;
import id.my.mdn.kupu.core.hr.entity.Position;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Singleton
@FacesConverter(managed = true, value = "PositionConverter")
public class PositionConverter implements Converter<Position> {
    
    @Inject
    private PositionFacade dao;

    @Override
    public Position getAsObject(FacesContext context, UIComponent component, String value) {
        return dao.find(KLong.valueOf(value));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Position value) {
        return value != null ? value.toString() : null;
    }   
    
}
