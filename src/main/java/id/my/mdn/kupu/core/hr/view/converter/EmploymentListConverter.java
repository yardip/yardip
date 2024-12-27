/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.core.hr.view.converter;

import id.my.mdn.kupu.core.base.util.EntityUtil;
import id.my.mdn.kupu.core.base.view.converter.SelectionsConverter;
import id.my.mdn.kupu.core.hr.dao.EmploymentFacade;
import id.my.mdn.kupu.core.hr.entity.Employment;
import id.my.mdn.kupu.core.party.entity.PartyRelationshipId;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Singleton
@FacesConverter(value = "EmploymentListConverter", managed = true)
public class EmploymentListConverter extends SelectionsConverter<Employment> {
    
    @Inject
    private EmploymentFacade dao;

    @Override
    public Employment getAsObject(String value) {
        return dao.find(new PartyRelationshipId(EntityUtil.parseCompositeId(value)));
    }

    @Override
    public String getAsString(Employment value) {
        return value != null ? value.toString() : null;
    }   
    
}
