/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.core.base.view.event;

import jakarta.enterprise.context.Dependent;
import jakarta.faces.event.PhaseEvent;
import jakarta.faces.event.PhaseId;
import jakarta.faces.event.PhaseListener;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Dependent
public class RequestProcessingListener implements PhaseListener{

    protected static Logger LOG = Logger.getLogger("Lifecycle");

    @Override
    public void beforePhase(PhaseEvent event) {
       LOG.log(Level.INFO, "BEFORE {0}", event.getPhaseId().getName());
    }

    @Override
    public void afterPhase(PhaseEvent event) {
        LOG.log(Level.INFO, "AFTER {0}", event.getPhaseId().getName());
    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.ANY_PHASE;
    }
    
}
