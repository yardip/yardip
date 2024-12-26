
package id.my.mdn.kupu.core.base.view.event;

import jakarta.faces.component.UIComponent;
import jakarta.faces.event.ComponentSystemEvent;
import jakarta.faces.event.NamedEvent;

/**
 *
 * @author aphasan
 */
@NamedEvent(shortName="postRestoreView")
public class PostRestoreViewEvent extends ComponentSystemEvent {
    
    public PostRestoreViewEvent(UIComponent component) {
        super(component);
    }
    
}
