
package id.my.mdn.kupu.core.base.view.converter;

import id.my.mdn.kupu.core.common.util.K.KInteger;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Singleton;

/**
 *
 * @author aphasan
 */
@Singleton
@FacesConverter(managed = true, value = "IntegerConverter")
public class IntegerConverter implements Converter<Integer>{

    @Override
    public Integer getAsObject(FacesContext context, UIComponent component, String value) {
        return value != null ? KInteger.valueOf(value) : null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Integer value) {
        return value != null ? String.valueOf(value) : null;
    }
    
}
