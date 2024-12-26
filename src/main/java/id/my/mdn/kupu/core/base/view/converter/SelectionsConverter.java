
package id.my.mdn.kupu.core.base.view.converter;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author aphasan
 * @param <E>
 */
public abstract class SelectionsConverter<E> implements Converter<List<E>> {

    protected abstract E getAsObject(String value);

    protected abstract String getAsString(E obj);

    @Override
    public List<E> getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null && !value.isEmpty()) {
            List<E> objs = new ArrayList<>();

            String decodedValue = null;
            try {
                decodedValue = URLDecoder.decode(value, "UTF-8");
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(SelectionsConverter.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (decodedValue != null) {
                String[] ids = decodedValue.split("\\+");
                for (String id : ids) {
                    E obj = getAsObject(id);
                    if (obj != null) {
                        objs.add(obj);
                    }
                }
            }

            return objs;
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, List<E> values) {
        if (values != null) {
            StringBuilder sb = new StringBuilder();
            int i = 1;
            for (E value : values) {
                sb.append(getAsString(value));
                if (i < values.size()) {
                    sb.append("+");
                }
                i++;
            }

            try {
                String encodedString = URLEncoder.encode(sb.toString(), "UTF-8");
                return encodedString;
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(SelectionsConverter.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        return "";
    }

}
