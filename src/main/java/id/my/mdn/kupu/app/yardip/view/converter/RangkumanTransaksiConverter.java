/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.yardip.view.converter;

import id.my.mdn.kupu.app.yardip.model.RangkumanTransaksi;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Singleton;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Singleton
@FacesConverter(value = "RangkumanTransaksiConverter", managed = true)
public class RangkumanTransaksiConverter implements Converter<RangkumanTransaksi> {

    @Override
    public RangkumanTransaksi getAsObject(FacesContext context, UIComponent component, String value) {
        return new RangkumanTransaksi(value);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, RangkumanTransaksi value) {
        return value != null ? value.getId() : null;
    }
    
}
