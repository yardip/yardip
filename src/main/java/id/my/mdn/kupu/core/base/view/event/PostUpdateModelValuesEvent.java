
package id.my.mdn.kupu.core.base.view.event;

import jakarta.faces.component.UIComponent;
import jakarta.faces.event.ComponentSystemEvent;
import jakarta.faces.event.NamedEvent;

/**
 *
 * @author aphasan
 */
@NamedEvent(shortName="postUpdateModelValues")
public class PostUpdateModelValuesEvent extends ComponentSystemEvent {
    
    public PostUpdateModelValuesEvent(UIComponent component) {
        super(component);
    }
    
}
