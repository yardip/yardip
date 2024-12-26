
package id.my.mdn.kupu.core.base.view.converter;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author aphasan
 */
@Named
@Singleton @FacesConverter(value = "String64ListConverter", managed = true)
public class String64ListConverter implements Converter<List<String>>{

    @Override
    public List<String> getAsObject(FacesContext context, UIComponent component, String value) {
        return Stream.of(value.split(";"))
                .map(str -> Base64.getDecoder().decode(str))
                .map(String::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, List<String> obj) {
        return obj.stream()
                .map(str-> Base64.getEncoder().encodeToString(str.getBytes()))
                .collect(Collectors.joining(";"));
    }
    
}
