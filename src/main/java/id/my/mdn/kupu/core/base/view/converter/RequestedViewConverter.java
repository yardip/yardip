
package id.my.mdn.kupu.core.base.view.converter;

import id.my.mdn.kupu.core.base.util.RequestedView;
import jakarta.enterprise.context.Dependent;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Named;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author aphasan
 */
@Named("RequestedViewConverter")
@Dependent @FacesConverter("RequestedViewConverter")
public class RequestedViewConverter implements Converter<RequestedView> {

    @Override
    public RequestedView getAsObject(FacesContext context, UIComponent component, String value) { 
        String decodedValue = value;
        
        String[] splittedUrl = decodedValue.split("\\?"); 
        
        RequestedView view = new RequestedView(splittedUrl[0]);
        
        if(splittedUrl.length > 1) {
            Arrays.asList(splittedUrl[1].split("&")).forEach(split->{                
                try {
                    split = URLDecoder.decode(split, "UTF-8");
                    String[] keyVal = split.split("=");
                    view.addParam(keyVal[0]).withValues(keyVal[1]);
                } catch (UnsupportedEncodingException ex) {
                    Logger.getLogger(RequestedViewConverter.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }
        
        return view;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, RequestedView value) {
        if(value == null) return "";
        return value.toString();
    }
    
}
