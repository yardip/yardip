/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.core.security.view.converter;

import id.my.mdn.kupu.core.base.view.converter.SelectionsConverter;
import id.my.mdn.kupu.core.security.dao.GroupAccessControlFacade;
import id.my.mdn.kupu.core.security.model.AccessControl;
import jakarta.faces.convert.FacesConverter;
import id.my.mdn.kupu.core.common.util.K.KLong;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Singleton
@FacesConverter(value = "AccessControlListConverter", managed = true)
public class AccessControlListConverter extends SelectionsConverter<AccessControl> {
    
    @Inject
    private GroupAccessControlFacade dao;

    @Override
    public AccessControl getAsObject(String value) {
        return dao.find(KLong.valueOf(value));
    }

    @Override
    public String getAsString(AccessControl value) {
        return value != null ? value.toString() : null;
    }

    
    
}
