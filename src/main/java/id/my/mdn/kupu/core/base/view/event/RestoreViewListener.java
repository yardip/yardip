
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
public class RestoreViewListener implements PhaseListener {
    
    @Inject
    private FacesContext context;

    @Override
    public void beforePhase(PhaseEvent event) {
    }
    
    @Override
    public void afterPhase(PhaseEvent event) {
        context.getApplication().publishEvent(context, 
                PostRestoreViewEvent.class, context.getViewRoot());
    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RESTORE_VIEW;
    }
    
}
