/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.security.view.converter;

import id.my.mdn.kupu.core.base.view.converter.SelectionsConverter;
import id.my.mdn.kupu.core.security.dao.ApplicationSecurityGroupFacade;
import id.my.mdn.kupu.core.security.model.ApplicationSecurityGroup;
import jakarta.faces.convert.FacesConverter;
import id.my.mdn.kupu.core.common.util.K.KLong;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

/**
 *
 * @author aphasan
 */
@Singleton
@FacesConverter(value = "ApplicationSecurityGroupListConverter", managed = true)
public class ApplicationSecurityGroupListConverter extends SelectionsConverter<ApplicationSecurityGroup> {

    @Inject
    private ApplicationSecurityGroupFacade service;

    @Override
    protected ApplicationSecurityGroup getAsObject(String value) {
        return service.find(KLong.valueOf(value));
    }

    @Override
    protected String getAsString(ApplicationSecurityGroup obj) {
        return obj.toString();
    }

}
