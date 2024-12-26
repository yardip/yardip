/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.security.view.widget;

import id.my.mdn.kupu.core.base.view.annotation.Default;
import id.my.mdn.kupu.core.base.view.widget.FilterContent;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Named;
import java.io.Serializable;

/**
 *
 * @author aphasan
 */
@Named(value = "securityGroupFilter")
@Dependent
public class ApplicationSecurityGroupFilter extends FilterContent implements Serializable {

    @Default("application")
    private String module;

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

}
