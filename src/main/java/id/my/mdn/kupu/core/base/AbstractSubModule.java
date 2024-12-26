/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.core.base;

import id.my.mdn.kupu.core.base.event.ModulePostInitEvent;
import jakarta.enterprise.event.ObservesAsync;

/**
 *
 * @author aphasan
 * @param <T>
 */
public abstract class AbstractSubModule<T extends AbstractModule> {    

    protected void init(@ObservesAsync @ModulePostInitEvent T module) {
        init();
        module.register(
                getLabel(),
                getClass().getPackage().getName(),
                getOrder(),
                enabled(),
                visible()
        );
        postInit();
    }

    protected abstract String getLabel();

    protected abstract int getOrder();

    protected boolean enabled() {
        return true;
    }

    protected boolean visible() {
        return true;
    }
    
    protected void init() {
        
    }

    protected void postInit() {
        
    }

}
