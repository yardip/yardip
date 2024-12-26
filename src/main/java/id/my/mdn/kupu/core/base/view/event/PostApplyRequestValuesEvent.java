
package id.my.mdn.kupu.core.base.view.event;

import jakarta.faces.component.UIComponent;
import jakarta.faces.event.ComponentSystemEvent;
import jakarta.faces.event.NamedEvent;

/**
 *
 * @author aphasan
 */
@NamedEvent(shortName="postApplyRequestValues")
public class PostApplyRequestValuesEvent extends ComponentSystemEvent {
    
    public PostApplyRequestValuesEvent(UIComponent component) {
        super(component);
    }
    
}
