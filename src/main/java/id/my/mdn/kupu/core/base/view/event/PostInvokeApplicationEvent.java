
package id.my.mdn.kupu.core.base.view.event;

import jakarta.faces.component.UIComponent;
import jakarta.faces.event.ComponentSystemEvent;
import jakarta.faces.event.NamedEvent;

/**
 *
 * @author aphasan
 */
@NamedEvent(shortName="postInvokeApplication")
public class PostInvokeApplicationEvent extends ComponentSystemEvent {
    
    public PostInvokeApplicationEvent(UIComponent component) {
        super(component);
    }
    
}
