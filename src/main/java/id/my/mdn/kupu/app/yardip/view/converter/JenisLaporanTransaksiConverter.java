/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.yardip.view.converter;

import id.my.mdn.kupu.app.yardip.model.JenisLaporanTransaksi;
import id.my.mdn.kupu.core.common.util.K.KEnum;
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
@FacesConverter(managed = true, value = "JenisLaporanTransaksiConverter")
public class JenisLaporanTransaksiConverter implements Converter<JenisLaporanTransaksi> {

    @Override
    public JenisLaporanTransaksi getAsObject(FacesContext context, UIComponent component, String value) {
        return KEnum.valueOf(JenisLaporanTransaksi.class, value);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, JenisLaporanTransaksi value) {
        return String.valueOf(value);
    }
    
}
