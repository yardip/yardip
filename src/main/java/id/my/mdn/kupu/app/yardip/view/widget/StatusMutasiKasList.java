/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.yardip.view.widget;

import id.my.mdn.kupu.app.yardip.model.StatusMutasiKas;
import id.my.mdn.kupu.core.base.view.widget.IValueList;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Named;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Named(value = "statusMutasiKasList")
@Dependent
public class StatusMutasiKasList  implements IValueList<StatusMutasiKas> {

    @Override
    public List<StatusMutasiKas> getFetchedItems() {
        return Arrays.asList(StatusMutasiKas.values());
    }    
    
}
