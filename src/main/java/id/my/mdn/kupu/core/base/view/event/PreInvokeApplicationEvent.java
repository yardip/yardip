
package id.my.mdn.kupu.core.base.view.event;

import jakarta.faces.component.UIComponent;
import jakarta.faces.event.ComponentSystemEvent;
import jakarta.faces.event.NamedEvent;

/**
 *
 * @author aphasan
 */
@NamedEvent(shortName="preInvokeApplication")
public class PreInvokeApplicationEvent extends ComponentSystemEvent {
    
    public PreInvokeApplicationEvent(UIComponent component) {
        super(component);
    }
    
}
