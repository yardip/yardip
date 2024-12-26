/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.core.local;

import id.my.mdn.kupu.core.base.AbstractModule;
import jakarta.enterprise.context.Dependent;

/**
 *
 * @author aphasan
 */
@Dependent
public class LocalModule extends AbstractModule {

    @Override
    protected String getLabel() {
        return "local.title";
    }

    @Override
    protected int getOrder() {
        return Integer.MAX_VALUE - 2;
    }

    @Override
    protected boolean visible() {
        return false;
    }
    
}
