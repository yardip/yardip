
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
public class ApplyRequestValuesListener implements PhaseListener {
    
    @Inject
    private FacesContext context;

    @Override
    public void beforePhase(PhaseEvent event) {
        context.getApplication().publishEvent(context, 
                PreApplyRequestValuesEvent.class, context.getViewRoot());
    }
    
    @Override
    public void afterPhase(PhaseEvent event) {
        context.getApplication().publishEvent(context, 
                PostApplyRequestValuesEvent.class, context.getViewRoot());
    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.APPLY_REQUEST_VALUES;
    }
    
}
