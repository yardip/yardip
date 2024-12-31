/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.yardip.view.converter;

import id.my.mdn.kupu.app.yardip.dao.BuktiKasFacade;
import id.my.mdn.kupu.app.yardip.entity.BuktiKas;
import id.my.mdn.kupu.app.yardip.entity.BuktiKasId;
import id.my.mdn.kupu.core.base.util.EntityUtil;
import id.my.mdn.kupu.core.base.view.converter.SelectionsConverter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Singleton
@FacesConverter(managed = true, value = "BuktiKasListConverter")
public class BuktiKasListConverter extends SelectionsConverter<BuktiKas> {

    @Inject
    private BuktiKasFacade dao;

    @Override
    public BuktiKas getAsObject(String value) {
        return dao.find(new BuktiKasId(EntityUtil.parseCompositeId(value)));
    }

    @Override
    public String getAsString(BuktiKas value) {
        return value != null ? String.valueOf(value) : null;
    }
    
}
