package id.my.mdn.kupu.core.base.view.converter;

import jakarta.enterprise.context.Dependent;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Named;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author aphasan
 */
@Named("QueryStringConverter")
@Dependent
@FacesConverter(value = "QueryStringConverter", managed = true)
public class QueryStringConverter implements Converter<String> {

    @Override
    public String getAsObject(FacesContext context, UIComponent component, String value) {
        String decodedValue = "";
        if (value != null) {
            try {
                decodedValue = URLDecoder.decode(value, "UTF-8");
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(QueryStringConverter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return decodedValue;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, String value) {
        return value;
    }

}
