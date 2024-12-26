
package id.my.mdn.kupu.core.base.view.event;

import jakarta.enterprise.context.Dependent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.PhaseEvent;
import jakarta.faces.event.PhaseId;
import jakarta.faces.event.PhaseListener;
import jakarta.inject.Inject;

/**
 *
 * @author aphasan
 */
@Dependent
public class InvokeApplicationListener implements PhaseListener {
    
    @Inject
    private FacesContext context;

    @Override
    public void beforePhase(PhaseEvent event) {
        context.getApplication().publishEvent(context, 
                PreInvokeApplicationEvent.class, context.getViewRoot());
    }
    
    @Override
    public void afterPhase(PhaseEvent event) {
        context.getApplication().publishEvent(context, 
                PostInvokeApplicationEvent.class, context.getViewRoot());
    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.INVOKE_APPLICATION;
    }
    
}
