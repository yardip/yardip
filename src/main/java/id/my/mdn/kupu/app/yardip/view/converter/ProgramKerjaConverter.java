/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.yardip.view.converter;

import id.my.mdn.kupu.app.yardip.model.ProgramKerja;
import id.my.mdn.kupu.core.common.util.K;
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
@FacesConverter(value = "ProgramKerjaConverter", managed = true)
public class ProgramKerjaConverter implements Converter<ProgramKerja> {

    @Override
    public ProgramKerja getAsObject(FacesContext context, UIComponent component, String value) {
        Long id = K.KLong.valueOf(value);
        if(id == null) return null;
        return new ProgramKerja(Long.valueOf(value));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, ProgramKerja value) {
        return value != null ? String.valueOf(value.getId()) : null;
    }

    
    
}
