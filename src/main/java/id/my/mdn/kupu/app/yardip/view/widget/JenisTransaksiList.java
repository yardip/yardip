/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.yardip.view.widget;

import id.my.mdn.kupu.app.yardip.entity.JenisTransaksi;
import id.my.mdn.kupu.core.base.dao.AbstractFacade;
import id.my.mdn.kupu.core.base.view.widget.AbstractValueList;
import id.my.mdn.kupu.core.base.util.FilterTypes.FilterData;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Named;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Named(value = "jenisTransaksiList")
@Dependent
public class JenisTransaksiList extends AbstractValueList<JenisTransaksi> {

    private Predicate<JenisTransaksi> filterIn = (x) -> true;

    @Override
    protected List<JenisTransaksi> getFetchedItemsInternal(Map<String, Object> parameters, List<FilterData> filters, List<SorterData> sorters, DefaultList<JenisTransaksi> defaultList, AbstractFacade.DefaultChecker defaultChecker) {
        return Arrays.asList(JenisTransaksi.values()).stream()
                .filter(filterIn)
                .collect(Collectors.toList());
    }

    public void setFilterIn(Predicate<JenisTransaksi> filterIn) {
        this.filterIn = filterIn;
    }

}
