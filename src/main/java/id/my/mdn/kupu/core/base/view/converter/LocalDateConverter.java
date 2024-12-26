/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.core.base.view.converter;

import id.my.mdn.kupu.core.base.util.Constants;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Singleton;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author aphasan
 */
@Singleton
@FacesConverter(value = "LocalDateConverter", managed = true)
public class LocalDateConverter implements Converter<LocalDate> {

    @Override
    public LocalDate getAsObject(FacesContext context, UIComponent component, String value) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constants.KEYFORMAT_LOCALDATE);
        return LocalDate.parse(value, formatter);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, LocalDate value) {
        if(value == null) return null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constants.KEYFORMAT_LOCALDATE);
        return value.format(formatter);
    }
}
