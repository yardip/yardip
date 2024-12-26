/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.core.mail;

import id.my.mdn.kupu.core.base.AbstractModule;
import jakarta.enterprise.context.Dependent;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Dependent
public class MailModule extends AbstractModule {

    @Override
    protected String getLabel() {
        return "mail.title";
    }

    @Override
    protected int getOrder() {
        return Integer.MAX_VALUE;
    }

    @Override
    protected boolean visible() {
        return false;
    }
    
}
