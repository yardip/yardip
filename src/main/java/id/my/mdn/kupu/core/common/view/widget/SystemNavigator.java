/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.core.common.view.widget;

import id.my.mdn.kupu.core.base.view.Page;
import id.my.mdn.kupu.core.base.view.widget.PageNavigator;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import java.io.Serializable;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Named
@ApplicationScoped
public class SystemNavigator extends PageNavigator implements Serializable {

    @Override
    protected Class<? extends Page> pageMap(String pageId) {
        return null;
    }

    @Override
    protected String getHome() {
        return "/";
    }

   
    
}
