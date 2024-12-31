/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.yardip.view.converter;

import id.my.mdn.kupu.app.yardip.dao.KasFacade;
import id.my.mdn.kupu.app.yardip.entity.Kas;
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
@FacesConverter(value = "KasListConverter", managed = true)
public class KasListConverter extends SelectionsConverter<Kas> {

    @Inject
    private KasFacade dao;

    @Override
    public Kas getAsObject(String value) {
        return dao.find(KLong.valueOf(value));
    }

    @Override
    public String getAsString(Kas value) {
        return value != null ? String.valueOf(value) : null;
    }
    
}
