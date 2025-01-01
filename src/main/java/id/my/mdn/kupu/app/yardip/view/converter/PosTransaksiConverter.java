/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.yardip.view.converter;

import id.my.mdn.kupu.app.yardip.dao.PosTransaksiFacade;
import id.my.mdn.kupu.app.yardip.model.PosTransaksi;
import id.my.mdn.kupu.core.common.util.K.KLong;
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
@FacesConverter(value = "PosTransaksiConverter", managed = true)
public class PosTransaksiConverter implements Converter<PosTransaksi> {
    
    @Inject
    private PosTransaksiFacade dao;

    @Override
    public PosTransaksi getAsObject(FacesContext context, UIComponent component, String value) {
        return dao.find(KLong.valueOf(value));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, PosTransaksi value) {
        return String.valueOf(value);
    }

    
    
}
