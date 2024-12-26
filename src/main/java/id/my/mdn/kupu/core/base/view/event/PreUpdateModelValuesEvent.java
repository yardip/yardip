
package id.my.mdn.kupu.core.base.view.event;

import jakarta.faces.component.UIComponent;
import jakarta.faces.event.ComponentSystemEvent;
import jakarta.faces.event.NamedEvent;

/**
 *
 * @author aphasan
 */
@NamedEvent(shortName="preUpdateModelValues")
public class PreUpdateModelValuesEvent extends ComponentSystemEvent {
    
    public PreUpdateModelValuesEvent(UIComponent component) {
        super(component);
    }
    
}
