/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.core.common;

import id.my.mdn.kupu.core.base.AbstractModule;
import jakarta.enterprise.context.Dependent;

/**
 *
 * @author Arief Prihasanto <aphasan at mdnx.dev>
 */
@Dependent
public class CommonModule extends AbstractModule {

    @Override
    protected String getLabel() {
        return "common.title";
    }

    @Override
    protected int getOrder() {
        return Integer.MAX_VALUE - 4;
    }

    @Override
    protected boolean visible() {
        return false;
    }
    
}
