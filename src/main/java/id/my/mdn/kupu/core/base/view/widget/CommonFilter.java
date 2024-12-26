/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.base.view.widget;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Named;
import java.io.Serializable;

/**
 *
 * @author aphasan
 */
@Named(value = "commonFilter")
@Dependent
public class CommonFilter extends FilterContent implements Serializable {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
