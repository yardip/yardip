/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.yardip.view.converter;

import id.my.mdn.kupu.app.yardip.model.RangkumanTransaksi;
import id.my.mdn.kupu.core.base.view.converter.SelectionsConverter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Singleton;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Singleton
@FacesConverter(value = "RangkumanTransaksiListConverter", managed = true)
public class RangkumanTransaksiListConverter extends SelectionsConverter<RangkumanTransaksi> {

    @Override
    public RangkumanTransaksi getAsObject(String value) {
        return new RangkumanTransaksi(value);
    }

    @Override
    public String getAsString(RangkumanTransaksi value) {
        return value != null ? value.getId() : null;
    }
    
}
