
package id.my.mdn.kupu.core.base.view.converter;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import id.my.mdn.kupu.core.common.util.K.KLong;
import jakarta.inject.Singleton;

/**
 *
 * @author aphasan
 */
@Singleton
@FacesConverter(managed = true, value = "LongConverter")
public class LongConverter implements Converter<Long>{

    @Override
    public Long getAsObject(FacesContext context, UIComponent component, String value) {
        return value != null ? KLong.valueOf(value) : null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Long value) {
        return value != null ? String.valueOf(value) : null;
    }
    
}
