/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.yardip.view;

import id.my.mdn.kupu.app.yardip.dao.KasFacade;
import id.my.mdn.kupu.app.yardip.dao.PosTransaksiFacade;
import id.my.mdn.kupu.app.yardip.dao.RangkumanTransaksiFacade;
import id.my.mdn.kupu.app.yardip.entity.JenisTransaksi;
import id.my.mdn.kupu.app.yardip.entity.PeriodFlag;
import id.my.mdn.kupu.app.yardip.view.widget.JenisTransaksiList;
import id.my.mdn.kupu.app.yardip.view.widget.TransaksiList;
import id.my.mdn.kupu.core.accounting.dao.AccountingPeriodFacade;
import id.my.mdn.kupu.core.accounting.entity.AccountingPeriod;
import id.my.mdn.kupu.core.accounting.view.widget.AccountingPeriodList;
import id.my.mdn.kupu.core.base.util.FilterTypes.FilterData;
import id.my.mdn.kupu.core.base.view.Page;
import id.my.mdn.kupu.core.base.view.annotation.Bookmarked;
import id.my.mdn.kupu.core.base.view.annotation.Creator;
import id.my.mdn.kupu.core.base.view.annotation.Deleter;
import id.my.mdn.kupu.core.base.view.annotation.Editor;
import id.my.mdn.kupu.core.base.view.widget.Selector;
import id.my.mdn.kupu.core.party.dao.BusinessEntityFacade;
import id.my.mdn.kupu.core.party.entity.BusinessEntity;
import id.my.mdn.kupu.core.party.view.widget.BusinessEntityList;
import jakarta.annotation.PostConstruct;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.event.ActionEvent;
import jakarta.faces.event.AjaxBehaviorEvent;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.security.enterprise.SecurityContext;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Named(value = "transaksiPage")
@ViewScoped
public class TransaksiPage extends Page implements Serializable {

    @Inject
    private BusinessEntityFacade entityFacade;

    @Inject
    private RangkumanTransaksiFacade rangkumanTransaksiFacade;

    @Inject
    private KasFacade kasFacade;

    @Inject
    private AccountingPeriodFacade periodFacade;

    @Inject
    private PosTransaksiFacade posFacade;

    @Inject
    private SecurityContext securityContext;

    @Inject
    private ExternalContext externalContext;

    @Bookmarked
    @Inject
    private TransaksiList dataList;

    @Bookmarked
    @Inject
    private AccountingPeriodList periodList;

    @Inject
    private BusinessEntityList businessEntityList;

    private BusinessEntity businessEntity;

    @Bookmarked(name = "yr")
    private Integer year = LocalDate.now().getYear();

    @Bookmarked
    @Inject
    private JenisTransaksiList jenisTransaksiList;

    @PostConstruct
    @Override
    protected void init() {
        String username = securityContext.getCallerPrincipal().getName();
        businessEntity = entityFacade.getByAppUsername(username);
        if (businessEntity == null) {
            try {
                externalContext.redirect("/app/yardip/view/entitas.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(TransaksiPage.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            periodFacade.generateMonthlyCalendarPeriod(businessEntity, PeriodFlag.BULANAN.name());
            kasFacade.createKasIfNotExist(businessEntity, "Tunai", "", LocalDate.EPOCH);
            posFacade.createUniquePosTransaksi(businessEntity, JenisTransaksi.TRANSFER_SALDO, "Sisa bulan lalu");
            posFacade.createUniquePosTransaksi(businessEntity, JenisTransaksi.MUTASI_KAS, "Mutasi Kas");
        }
        super.init();

        periodList.setSelectionMode(() -> Selector.SINGLE);
        periodList.getSelector().setSelectionsLabel("ps");
        periodList.getFilter().setStaticFilter(this::periodFilters);

        dataList.setHiddenParameters(this::parameters);

        jenisTransaksiList.getSelector().setSelectionsLabel("ttrx");
        jenisTransaksiList.setFilterIn(jenis -> (jenis.equals(JenisTransaksi.PENERIMAAN)
                || jenis.equals(JenisTransaksi.PENGELUARAN)
                || jenis.equals(JenisTransaksi.MUTASI_KAS))
        );

    }

    public void postInit() {
        if (periodList.getSelection() == null) {
            AccountingPeriod currentPeriod = periodFacade.getCurrentPeriod(businessEntity);
            periodList.getSelector().setSelectionInternal(currentPeriod);
        }

        if (periodList.getSelection().getFromDate().getMonth().compareTo(LocalDate.now().getMonth()) < 1) {
            rangkumanTransaksiFacade.calculateTransferSaldo(businessEntity, periodList.getSelection());
        }
    }

    private List<FilterData> periodFilters() {
        return List.of(FilterData.by("businessEntity", businessEntity),
                FilterData.by("year", year != null ? year : 3000),
                FilterData.by("flag", PeriodFlag.BULANAN)
        );
    }

    private Map<String, Object> parameters() {

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("entity", businessEntity);
        parameters.put("period", periodList.getSelection());
        parameters.put("trxType", jenisTransaksiList.getSelection());
        return parameters;
    }

    public List<String> getdistinctKasByName() {
        return kasFacade.findDistinctKasByName(businessEntity, periodList.getSelection());
    }

    public int getDistinctKasCount() {
        AccountingPeriod selectedPeriod = periodList.getSelection();
        if (selectedPeriod == null) {
            return 0;
        }
        return kasFacade.findDistinctKasByName(businessEntity, selectedPeriod).size();
    }

    public void onChangeBusinessEntity(AjaxBehaviorEvent evt) {
        periodList.reset();
        dataList.reset();
        postInit();
    }

    public void onChangeYear(AjaxBehaviorEvent evt) {
        periodList.reset();
        dataList.reset();
    }

    public void onChangePeriod(AjaxBehaviorEvent evt) {
        if (periodList.getSelection().getFromDate().getMonth().compareTo(LocalDate.now().getMonth()) < 1) {
            rangkumanTransaksiFacade.calculateTransferSaldo(businessEntity, periodList.getSelection());
        }
        dataList.reset();
    }

    public void onChangeJenis(AjaxBehaviorEvent evt) {
        dataList.reset();
    }

    @Creator(of = "dataList")
    public void openTransaksiCreator() {
        gotoChild(TransaksiEditorPage.class)
                .addParam("period")
                .withValues(periodList.getSelection())
                .open();
    }

    @Editor(of = "dataList")
    public void openTransaksiEditor() {
        gotoChild(TransaksiEditorPage.class)
                .addParam("entity")
                .withValues(dataList.getSelected())
                .addParam("period")
                .withValues(periodList.getSelection())
                .open();
    }

    @Deleter(of = "dataList")
    public void openTransaksiDeleter() {
        dataList.deleteSelections();
    }

    public void copyTransaksi() {
        
        gotoChild(TransaksiEditorPage.class)
                .addParam("entity")
                .withValues(dataList.getSelected())
                .addParam("cp")
                .withValues(Boolean.TRUE)
                .open();
    }

    public void viewBuktiKas(ActionEvent evt) {
        gotoChild(BuktiKasPage.class)
                .addParam("trx")
                .withValues(dataList.getSelected())
                .open();
    }

    public List<Integer> getYearList() {
        return periodFacade.getAvailableYears(businessEntity);
    }

    public void prepareReport(ActionEvent evt) {
        gotoChild(TransaksiReportPage.class)
                .addParam("ps")
                .withValues(periodList.getSelection())
                .open();
    }

    public TransaksiList getDataList() {
        return dataList;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public AccountingPeriodList getPeriodList() {
        return periodList;
    }

    public BusinessEntityList getBusinessEntityList() {
        return businessEntityList;
    }

    public BusinessEntity getBusinessEntity() {
        return businessEntity;
    }

    public void setBusinessEntity(BusinessEntity businessEntity) {
        this.businessEntity = businessEntity;
    }

    public JenisTransaksiList getJenisTransaksiList() {
        return jenisTransaksiList;
    }

}
