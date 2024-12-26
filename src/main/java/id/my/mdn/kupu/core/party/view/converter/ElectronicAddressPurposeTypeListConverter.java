/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.party.view.converter;

import id.my.mdn.kupu.core.base.view.converter.SelectionsConverter;
import id.my.mdn.kupu.core.party.dao.ElectronicAddressPurposeTypeFacade;
import id.my.mdn.kupu.core.party.entity.ElectronicAddressPurposeType;
import jakarta.enterprise.context.Dependent;
import jakarta.faces.convert.FacesConverter;
import id.my.mdn.kupu.core.common.util.K.KLong;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 *
 * @author aphasan
 */
@Named(value = "ElectronicAddressPurposeTypeListConverter")
@Dependent @FacesConverter(value = "ElectronicAddressPurposeTypeListConverter", managed = true)
public class ElectronicAddressPurposeTypeListConverter extends SelectionsConverter<ElectronicAddressPurposeType> {
    
    @Inject
    private ElectronicAddressPurposeTypeFacade service;

    @Override
    public ElectronicAddressPurposeType getAsObject(String value) {
       return service.find(KLong.valueOf(value));
    }

    @Override
    public String getAsString(ElectronicAddressPurposeType value) {
        return value != null ? value.toString() : null;
    }
    
}
