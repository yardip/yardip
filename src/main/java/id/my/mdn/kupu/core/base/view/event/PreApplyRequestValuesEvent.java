
package id.my.mdn.kupu.core.base.view.event;

import jakarta.faces.component.UIComponent;
import jakarta.faces.event.ComponentSystemEvent;
import jakarta.faces.event.NamedEvent;

/**
 *
 * @author aphasan
 */
@NamedEvent(shortName="preApplyRequestValues")
public class PreApplyRequestValuesEvent extends ComponentSystemEvent {
    
    public PreApplyRequestValuesEvent(UIComponent component) {
        super(component);
    }
    
}
