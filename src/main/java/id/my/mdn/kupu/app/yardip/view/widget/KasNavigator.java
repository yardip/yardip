/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.yardip.view.widget;

import id.my.mdn.kupu.app.yardip.view.KasPage;
import id.my.mdn.kupu.app.yardip.view.ProgramKerjaPage;
import id.my.mdn.kupu.core.base.view.Page;
import id.my.mdn.kupu.core.base.view.widget.PageNavigator;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import java.io.Serializable;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Named(value = "kasNavigator")
@ApplicationScoped
public class KasNavigator extends PageNavigator implements Serializable {

    @Override
    protected Class<? extends Page> pageMap(String pageId) {
        switch(pageId) {            
            case "ProgramKerja":
                return ProgramKerjaPage.class;            
            case "Kas":
                return KasPage.class;
            default:
                return null;
        }
    }

    @Override
    protected String getHome() {        
        return "/app/yardip/index.xhtml";
    }

    
    
}
