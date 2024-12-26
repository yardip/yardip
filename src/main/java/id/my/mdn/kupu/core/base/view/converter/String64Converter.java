package id.my.mdn.kupu.core.base.view.converter;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import java.util.Base64;

/**
 *
 * @author aphasan
 */
@Named
@Singleton
@FacesConverter(value = "String64Converter", managed = true)
public class String64Converter implements Converter<String> {

    @Override
    public String getAsObject(FacesContext context, UIComponent component, String value) {
        return new String(Base64.getDecoder().decode(value));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, String obj) {
        return Base64.getEncoder().encodeToString(obj.getBytes());
    }

}
