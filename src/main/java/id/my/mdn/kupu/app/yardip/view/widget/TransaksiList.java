/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.yardip.view.widget;

import id.my.mdn.kupu.app.yardip.dao.KasFacade;
import id.my.mdn.kupu.app.yardip.dao.RangkumanTransaksiFacade;
import id.my.mdn.kupu.app.yardip.entity.JenisTransaksi;
import id.my.mdn.kupu.app.yardip.entity.RangkumanTransaksi;
import id.my.mdn.kupu.app.yardip.entity.RangkumanTransaksi.KasAmount;
import id.my.mdn.kupu.core.accounting.entity.AccountingPeriod;
import id.my.mdn.kupu.core.base.dao.AbstractFacade.DefaultChecker;
import id.my.mdn.kupu.core.base.util.FilterTypes.FilterData;
import id.my.mdn.kupu.core.base.view.widget.AbstractMutablePagedValueList;
import id.my.mdn.kupu.core.party.entity.BusinessEntity;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Named(value = "transaksiList")
@Dependent
public class TransaksiList extends AbstractMutablePagedValueList<RangkumanTransaksi> {

    @Inject
    private RangkumanTransaksiFacade dao;

    @Inject
    private KasFacade kasFacade;

    @Inject
    private TransaksiFilter filterContent;

    @PostConstruct
    public void init() {
        filter.setContent(filterContent);

        getSorters().add(SorterData.by("trxdate"));
        getSorters().add(SorterData.by("created"));

//        setDefaultChecker(this::returnDefault);
//        setDefaultList(() -> new ArrayList<>());
//        setDefaultCount(() -> 0L);
    }

    private boolean returnDefault() {
        return hiddenParameters.get().get("period") == null;
    }

    @Override
    protected List<RangkumanTransaksi> getPagedFetchedItemsInternal(
            int first, int pageSize, Map<String, Object> parameters,
            List<FilterData> filters, List<SorterData> sorters,
            DefaultList<RangkumanTransaksi> defaultList, DefaultChecker defaultChecker) {
//        if (defaultChecker.passed()) {
//            return defaultList.get();
//        }
        return getListRangkuman(first, pageSize, parameters, filters, sorters);
    }

    @Override
    protected long getItemsCountInternal(
            Map<String, Object> parameters, List<FilterData> filters,
            DefaultCount defaultCount, DefaultChecker defaultChecker) {

//        if (defaultChecker.passed()) {
//            return defaultCount.get();
//        }
        BusinessEntity entity = (BusinessEntity) parameters.get("entity");
        AccountingPeriod period = (AccountingPeriod) parameters.get("period");
        JenisTransaksi trxType = (JenisTransaksi) parameters.get("trxType");

        List<String> listKasName = kasFacade.findDistinctKasByName(entity, period);

        return dao.countAll(
                () -> dao.generateQuery(listKasName, trxType),
                parameters, filters,
                defaultCount.get(), defaultChecker);
    }

    private List<RangkumanTransaksi> getListRangkuman(
            int first, int pageSize, Map<String, Object> parameters,
            List<FilterData> filters, List<SorterData> sorters) {
        BusinessEntity entity = (BusinessEntity) parameters.get("entity");
        AccountingPeriod period = (AccountingPeriod) parameters.get("period");
        JenisTransaksi trxType = (JenisTransaksi) parameters.get("trxType");

        List<String> listKasName = kasFacade.findDistinctKasByName(
                entity, period
        );

        List<RangkumanTransaksi> result = dao.findAll(
                () -> dao.generateQuery(listKasName, trxType),
                first, pageSize, parameters,
                filters, sorters, null, null)
                .stream().map(RangkumanTransaksi::new)
                .collect(Collectors.toList());

        RangkumanTransaksi total = dao.caculateTotal(entity, period, trxType);

        if (total != null && total.getAmountDebit() != null && total.getAmountCredit() != null) {
            result.add(total);

            if (trxType == null) {
                BigDecimal[] listKas = new BigDecimal[total.getListKasAmount().size()];
                int i = 0;
                for (KasAmount kasAmount : total.getListKasAmount()) {
                    listKas[i++] = kasAmount.getDebit().add(kasAmount.getCredit());
                }
                RangkumanTransaksi saldo = new RangkumanTransaksi(
                        "00",
                        null,
                        "Sisa akhir bulan (Debit - Kredit)",
                        null,
                        total.getAmountDebit().add(total.getAmountCredit()),
                        listKas);
                result.add(saldo);
            }
        }

        return result;

    }

    @Override
    protected void createInternal(RangkumanTransaksi entity) {

    }

    @Override
    protected void deleteInternal(RangkumanTransaksi entity) {
        dao.remove(entity);
    }

    @Override
    public void edit(RangkumanTransaksi entity) {

    }

}
