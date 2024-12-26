/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.core.config;

import id.my.mdn.kupu.core.base.AbstractModule;
import jakarta.enterprise.context.Dependent;

/**
 *
 * @author aphasan
 */
@Dependent
public class ConfigModule extends AbstractModule {

    @Override
    protected String getLabel() {
        return "config.title";
    }

    @Override
    protected int getOrder() {
        return Integer.MAX_VALUE - 1;
    }

    @Override
    protected boolean visible() {
        return false;
    }
    
}
