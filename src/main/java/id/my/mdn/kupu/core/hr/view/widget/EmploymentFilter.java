/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.core.hr.view.widget;

import id.my.mdn.kupu.core.base.view.annotation.Bookmarked;
import id.my.mdn.kupu.core.base.view.widget.FilterContent;
import id.my.mdn.kupu.core.hr.entity.Position;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Named;
import java.io.Serializable;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Named(value = "employmentFilter")
@Dependent
public class EmploymentFilter extends FilterContent implements Serializable {

    @Bookmarked
    private Position position;

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
    
}
