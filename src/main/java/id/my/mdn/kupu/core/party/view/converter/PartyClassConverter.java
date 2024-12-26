/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.party.view.converter;

import id.my.mdn.kupu.core.party.entity.Organization;
import id.my.mdn.kupu.core.party.entity.Party;
import id.my.mdn.kupu.core.party.entity.Person;
import jakarta.enterprise.context.Dependent;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Named;

/**
 *
 * @author aphasan
 */
@Named("PartyClassConverter")
@Dependent @FacesConverter(value = "PartyClassConverter", managed = true)
public class PartyClassConverter implements Converter<Class<? extends Party>> {

    @Override
    public Class<? extends Party> getAsObject(FacesContext context, UIComponent component, String value) {
        switch (value) {
            case "Person":
                return Person.class;
            case "Organization":
                return Organization.class;
            default:
                return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Class<? extends Party> value) {
        if (value != null) {
            return value.getSimpleName();
        } else {
            return null;
        }
    }

}
