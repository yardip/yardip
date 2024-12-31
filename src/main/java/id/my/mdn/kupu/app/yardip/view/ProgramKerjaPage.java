/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.yardip.view;

import id.my.mdn.kupu.app.yardip.entity.JenisTransaksi;
import id.my.mdn.kupu.app.yardip.entity.PeriodFlag;
import id.my.mdn.kupu.app.yardip.view.widget.JenisTransaksiList;
import id.my.mdn.kupu.app.yardip.view.widget.ProgramKerjaFilter;
import id.my.mdn.kupu.app.yardip.view.widget.ProgramKerjaList;
import id.my.mdn.kupu.core.accounting.dao.AccountingPeriodFacade;
import id.my.mdn.kupu.core.accounting.entity.AccountingPeriod;
import id.my.mdn.kupu.core.accounting.view.widget.AccountingPeriodList;
import id.my.mdn.kupu.core.base.util.FilterTypes.FilterData;
import id.my.mdn.kupu.core.base.view.Page;
import id.my.mdn.kupu.core.base.view.annotation.Bookmarked;
import id.my.mdn.kupu.core.base.view.annotation.Creator;
import id.my.mdn.kupu.core.base.view.annotation.Deleter;
import id.my.mdn.kupu.core.base.view.annotation.Editor;
import static id.my.mdn.kupu.core.base.view.widget.Selector.SINGLE;
import id.my.mdn.kupu.core.party.dao.BusinessEntityFacade;
import id.my.mdn.kupu.core.party.entity.BusinessEntity;
import id.my.mdn.kupu.core.party.view.widget.BusinessEntityList;
import jakarta.annotation.PostConstruct;
import jakarta.faces.event.ActionEvent;
import jakarta.faces.event.AjaxBehaviorEvent;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.security.enterprise.SecurityContext;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Named(value = "programKerjaPage")
@ViewScoped
public class ProgramKerjaPage extends Page implements Serializable {

    @Bookmarked
    @Inject
    private ProgramKerjaList dataView;

    @Bookmarked
    @Inject
    private AccountingPeriodList periodList;

    @Bookmarked(name = "yr")
    private Integer year = LocalDate.now().getYear();

    @Inject
    private JenisTransaksiList jenisTransaksiList;

    @Inject
    private BusinessEntityFacade entityFacade;
    
    @Inject
    private BusinessEntityList businessEntityList;

    private BusinessEntity businessEntity;
    
    @Inject
    private SecurityContext securityContext;

    @Inject
    private AccountingPeriodFacade periodFacade;

    @PostConstruct
    @Override
    protected void init() {
        String username = securityContext.getCallerPrincipal().getName();        
        businessEntity = entityFacade.getByAppUsername(username);
        
        super.init();
        dataView.setParameters(this::parameters);

        dataView.setHiddenParameters(this::parameters);

        periodList.setSelectionMode(() -> SINGLE);
        periodList.getSelector().setSelectionsLabel("ps");
        periodList.getFilter().setStaticFilter(this::periodFilters);

        jenisTransaksiList.setSelectionMode(() -> SINGLE);
        jenisTransaksiList.setFilterIn(jenis -> (jenis.equals(JenisTransaksi.PENERIMAAN)
                || jenis.equals(JenisTransaksi.PENGELUARAN))
        );
    }

    public void postInit() {
        if (periodList.getSelection() == null) {
            AccountingPeriod currentPeriod = periodFacade.getCurrentPeriod(businessEntity);
            periodList.getSelector().setSelectionInternal(currentPeriod);
        }
        JenisTransaksi jenisTransaksi = dataView.getFilter().<ProgramKerjaFilter>getContent().getJenisTransaksi();
        if(jenisTransaksi == null) jenisTransaksi = JenisTransaksi.PENERIMAAN;
        dataView.getFilter()
                .<ProgramKerjaFilter>getContent()
                .setJenisTransaksi(jenisTransaksi);
        dataView.getFilter().setFiltering(true);
    }

    private Map<String, Object> parameters() {

        AccountingPeriod periodSelection = periodList.getSelection();

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("entity", businessEntity);
        parameters.put("period", periodSelection);
        return parameters;
    }

    private List<FilterData> periodFilters() {
        return List.of(FilterData.by("businessEntity", businessEntity),
                FilterData.by("year", year != null ? year : 3000),
                FilterData.by("flag", PeriodFlag.BULANAN)
        );
    }

    @Creator(of = "dataView")
    public void openProgramKerjaCreator() {
        gotoChild(ProgramKerjaEditorPage.class)
                .addParam("tp")
                .withValues(dataView.getFilter().<ProgramKerjaFilter>getContent().getJenisTransaksi())
                .open();
    }

    @Editor(of = "dataView")
    public void openProgramKerjaEditor() { 
        gotoChild(ProgramKerjaEditorPage.class)
                .addParam("entity")
                .withValues(dataView.getSelected())
                .open();
    }

    @Deleter(of = "dataView")
    public void openProgramKerjaDeleter() {
        dataView.deleteSelections();
    }

    public void prepareReport(ActionEvent evt) {
        gotoChild(ProgramKerjaReportPage.class)
                .addParam("ps")
                .withValues(periodList.getSelection())
                .addParam("tp")
                .withValues(dataView.getFilter().<ProgramKerjaFilter>getContent().getJenisTransaksi())
                .addParam("yr")
                .withValues(year)
                .open();
    }

    public void onChangeBusinessEntity(AjaxBehaviorEvent evt) {
        periodList.reset();
        dataView.reset();
        postInit();
    }

    public void onChangeYear(AjaxBehaviorEvent evt) {
        periodList.reset();
        doFilter(null);
    }

    public void doFilter(AjaxBehaviorEvent evt) {
        dataView.reset();
    }

    public List<Integer> getYearList() {
        return periodFacade.getAvailableYears(businessEntity);
    }

    public BusinessEntityList getBusinessEntityList() {
        return businessEntityList;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public ProgramKerjaList getDataView() {
        return dataView;
    }

    public AccountingPeriodList getPeriodList() {
        return periodList;
    }

    public void setPeriodList(AccountingPeriodList periodList) {
        this.periodList = periodList;
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
