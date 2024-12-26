
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
public class UpdateModelValuesListener implements PhaseListener {
    
    @Inject
    private FacesContext context;

    @Override
    public void beforePhase(PhaseEvent event) {
        context.getApplication().publishEvent(context, 
                PreUpdateModelValuesEvent.class, context.getViewRoot());
    }
    
    @Override
    public void afterPhase(PhaseEvent event) {
        context.getApplication().publishEvent(context, 
                PostUpdateModelValuesEvent.class, context.getViewRoot());
    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.UPDATE_MODEL_VALUES;
    }
    
}
