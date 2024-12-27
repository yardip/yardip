/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.core.hr.view.converter;

import id.my.mdn.kupu.core.base.util.LogUtil;
import id.my.mdn.kupu.core.base.view.converter.SelectionsConverter;
import id.my.mdn.kupu.core.common.util.K.KLong;
import id.my.mdn.kupu.core.hr.dao.PositionFacade;
import id.my.mdn.kupu.core.hr.entity.Position;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Singleton
@FacesConverter(value = "PositionListConverter", managed = true)
public class PositionListConverter extends SelectionsConverter<Position> {

    @Inject
    private PositionFacade dao;

    @Override
    public Position getAsObject(String value) {
        Position position = dao.find(KLong.valueOf(value));
        LogUtil.logMethod(this, position);
        return position;
    }

    @Override
    public String getAsString(Position value) {
        return value != null ? value.toString() : null;
    }
    
}
