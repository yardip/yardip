/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.core.reporting.ds;

import java.util.Collection;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
public class BeanCollectionDataSource implements ReportDataSource {
    
    private final Collection beanCollection;

    public BeanCollectionDataSource(Collection beanCollection) {
        this.beanCollection = beanCollection;
    }
    
    @Override
    public Object get() {
        return new JRBeanCollectionDataSource(beanCollection);
    }
    
}
