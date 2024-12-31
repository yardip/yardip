/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.yardip.view.widget;

import id.my.mdn.kupu.app.yardip.entity.JenisLaporanTransaksi;
import id.my.mdn.kupu.core.base.view.widget.IValueList;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Named;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Named(value = "jenisLaporanTransaksiList")
@Dependent
public class JenisLaporanTransaksiList implements IValueList<JenisLaporanTransaksi> {
    
    private Predicate<JenisLaporanTransaksi> filterIn = (x) -> true;

    @Override
    public List<JenisLaporanTransaksi> getFetchedItems() {
        return Arrays.asList(JenisLaporanTransaksi.values()).stream()
                .filter(filterIn)
                .collect(Collectors.toList());
    } 

    public void setFilterIn(Predicate<JenisLaporanTransaksi> filterIn) {
        this.filterIn = filterIn;
    }
    
}
