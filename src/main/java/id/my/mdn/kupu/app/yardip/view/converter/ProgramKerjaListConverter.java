/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.yardip.view.converter;

import id.my.mdn.kupu.app.yardip.entity.ProgramKerja;
import id.my.mdn.kupu.core.base.view.converter.SelectionsConverter;
import id.my.mdn.kupu.core.common.util.K.KLong;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Singleton;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Singleton
@FacesConverter(value = "ProgramKerjaListConverter", managed = true)
public class ProgramKerjaListConverter extends SelectionsConverter<ProgramKerja> {

    @Override
    public ProgramKerja getAsObject(String value) {
        Long id = KLong.valueOf(value);
        if(id == null) return null;
        return new ProgramKerja(Long.valueOf(value));
    }

    @Override
    public String getAsString(ProgramKerja value) {
        return value != null ? String.valueOf(value.getId()) : null;
    }

    
    
}
