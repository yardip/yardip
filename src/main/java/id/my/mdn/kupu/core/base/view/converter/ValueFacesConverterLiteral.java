/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.core.base.view.converter;

import jakarta.enterprise.util.AnnotationLiteral;
import jakarta.faces.convert.FacesConverter;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
public class ValueFacesConverterLiteral extends AnnotationLiteral<FacesConverter> implements jakarta.faces.convert.FacesConverter{
    
    private Class forClass = new Object().getClass();
    
    private String value = "";

    public ValueFacesConverterLiteral(Class forClass) {
        this.forClass = forClass;
    }

    public ValueFacesConverterLiteral(String string, boolean byName) throws ClassNotFoundException {
        if(byName) this.value = string;
        else this.forClass = Class.forName(string);
    }

    public ValueFacesConverterLiteral(String className) throws ClassNotFoundException {
        this(className, false);
    }

    public Class getForClass() {
        return forClass;
    }

    @Override
    public String value() {
        return value;
    }

    @Override
    public Class forClass() {
        return forClass;
    }

    @Override
    public boolean managed() {
        return true;
    }
    
}
