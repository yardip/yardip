/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.core.base.view.util;

import id.my.mdn.kupu.core.base.view.converter.FacesConverterLiteral;
import jakarta.enterprise.inject.Instance;
import jakarta.enterprise.inject.spi.CDI;
import jakarta.faces.convert.Converter;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
public class ConverterUtil {    

    public static Converter findConverter(CDI<Object> context, Class<?> cls) {
        return findConverter(context, cls.getSimpleName() + "Converter");
    }

    public static Converter findConverter(CDI<Object> context, String converterName) {
        Instance<Object> converterInstance = context.select(new FacesConverterLiteral(converterName));

        if (converterInstance.isUnsatisfied() || !converterInstance.isResolvable() || converterInstance.isAmbiguous()) {
            return null;
        }

        return (Converter) converterInstance.get();
    }
}
