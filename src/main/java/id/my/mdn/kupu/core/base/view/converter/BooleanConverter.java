
package id.my.mdn.kupu.core.base.view.converter;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import id.my.mdn.kupu.core.common.util.K.KBoolean;
import jakarta.inject.Singleton;

/**
 *
 * @author aphasan
 */
@Singleton
@FacesConverter(managed = true, value = "BooleanConverter")
public class BooleanConverter implements Converter<Boolean> {

    @Override
    public Boolean getAsObject(FacesContext context, UIComponent component, String value) {
        return KBoolean.valueOf(value);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Boolean value) {
        return Boolean.toString(value);
    }
    
}
