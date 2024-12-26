/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.core.base.view.event;

import jakarta.enterprise.context.Dependent;
import jakarta.faces.event.PhaseEvent;
import jakarta.faces.event.PhaseId;
import jakarta.faces.event.PhaseListener;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Dependent
public class RequestProcessingListener implements PhaseListener{

    @Override
    public void beforePhase(PhaseEvent event) {
        System.out.println("SELEK BIPOR " + event.getPhaseId().getName());
    }

    @Override
    public void afterPhase(PhaseEvent event) {
        System.out.println("SELEK APTER " + event.getPhaseId().getName());
        System.out.println("SELEK===========================================SELEK");
    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.ANY_PHASE;
    }
    
}
