/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.yardip;

import id.my.mdn.kupu.core.base.AbstractModule;
import jakarta.enterprise.context.Dependent;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Dependent
public class YardipModule extends AbstractModule {

    @Override
    protected String getLabel() {
        return "Yayasan";
    }

    @Override
    protected int getOrder() {
        return 1000;
    }

}
