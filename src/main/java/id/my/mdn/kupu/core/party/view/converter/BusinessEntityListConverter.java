/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.core.party.view.converter;

import id.my.mdn.kupu.core.party.dao.BusinessEntityFacade;
import id.my.mdn.kupu.core.party.entity.BusinessEntity;
import id.my.mdn.kupu.core.base.util.LogUtil;
import id.my.mdn.kupu.core.base.view.converter.SelectionsConverter;
import jakarta.faces.convert.FacesConverter;
import id.my.mdn.kupu.core.common.util.K.KLong;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Singleton
@FacesConverter(value = "BusinessEntityListConverter", managed = true)
public class BusinessEntityListConverter extends SelectionsConverter<BusinessEntity> {

    @Inject
    private BusinessEntityFacade dao;

    @Override
    public BusinessEntity getAsObject(String value) {
        BusinessEntity entity = dao.find(KLong.valueOf(value));
        LogUtil.logMethod(this, entity);
        return entity;
    }

    @Override
    public String getAsString(BusinessEntity value) {
        return value != null ? value.toString() : null;
    }
    
}
