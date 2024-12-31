/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.yardip.view.converter;

import id.my.mdn.kupu.app.yardip.dao.PosTransaksiFacade;
import id.my.mdn.kupu.app.yardip.entity.PosTransaksi;
import id.my.mdn.kupu.core.base.view.converter.SelectionsConverter;
import id.my.mdn.kupu.core.common.util.K.KLong;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Singleton
@FacesConverter(value = "PosTransaksiListConverter", managed = true)
public class PosTransaksiListConverter extends SelectionsConverter<PosTransaksi> {
    
    @Inject
    private PosTransaksiFacade dao;

    @Override
    public PosTransaksi getAsObject(String value) {
        return dao.find(KLong.valueOf(value));
    }

    @Override
    public String getAsString(PosTransaksi value) {
        return String.valueOf(value);
    }

    
    
}
