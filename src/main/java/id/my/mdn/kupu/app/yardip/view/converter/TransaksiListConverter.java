/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.yardip.view.converter;

import id.my.mdn.kupu.app.yardip.dao.TransaksiFacade;
import id.my.mdn.kupu.app.yardip.model.Transaksi;
import id.my.mdn.kupu.core.base.view.converter.SelectionsConverter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Singleton
@FacesConverter(managed = true, value = "TransaksiListConverter")
public class TransaksiListConverter extends SelectionsConverter<Transaksi> {

    @Inject
    private TransaksiFacade dao;

    @Override
    public Transaksi getAsObject(String value) {
        return dao.find(value);
    }

    @Override
    public String getAsString(Transaksi value) {
        return value != null ? value.getId() : null;
    }
    
}
