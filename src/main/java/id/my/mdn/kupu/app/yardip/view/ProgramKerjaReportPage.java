/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.yardip.view;

import id.my.mdn.kupu.app.yardip.dao.ProgramKerjaFacade;
import id.my.mdn.kupu.app.yardip.model.JenisTransaksi;
import id.my.mdn.kupu.app.yardip.model.PeriodFlag;
import id.my.mdn.kupu.app.yardip.model.ProgramKerja;
import id.my.mdn.kupu.app.yardip.view.widget.JenisTransaksiList;
import id.my.mdn.kupu.app.yardip.view.widget.ProgramKerjaFilter;
import id.my.mdn.kupu.core.accounting.dao.AccountingPeriodFacade;
import id.my.mdn.kupu.core.accounting.entity.AccountingPeriod;
import id.my.mdn.kupu.core.accounting.view.widget.AccountingPeriodList;
import id.my.mdn.kupu.core.base.util.FilterTypes.FilterData;
import id.my.mdn.kupu.core.base.view.annotation.Bookmarked;
import id.my.mdn.kupu.core.base.view.widget.Filter;
import id.my.mdn.kupu.core.base.view.widget.IValueList.SorterData;
import static id.my.mdn.kupu.core.base.view.widget.Selector.SINGLE;
import id.my.mdn.kupu.core.party.dao.BusinessEntityFacade;
import id.my.mdn.kupu.core.party.dao.OrganizationFacade;
import id.my.mdn.kupu.core.party.entity.BusinessEntity;
import id.my.mdn.kupu.core.reporting.model.ReportingJob;
import id.my.mdn.kupu.core.reporting.view.ReportingChildPage;
import jakarta.annotation.PostConstruct;
import jakarta.faces.event.AjaxBehaviorEvent;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.security.enterprise.SecurityContext;
import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Named(value = "programKerjaReportPage")
@ViewScoped
public class ProgramKerjaReportPage extends ReportingChildPage implements Serializable {

    @Inject
    private ProgramKerjaFacade dao;

    @Inject
    private BusinessEntityFacade entityFacade;
    
    @Inject
    private OrganizationFacade organizationFacade;

    @Inject
    private AccountingPeriodFacade periodFacade;

    @Bookmarked
    @Inject
    private AccountingPeriodList periodList;

    @Bookmarked(name = "yr")
    private Integer year = LocalDate.now().getYear();

    @Inject
    private SecurityContext securityContext;
    
    @Bookmarked
    private Filter filter;
    
    @Inject
    private ProgramKerjaFilter filterContent;
    
    @Inject
    private JenisTransaksiList jenisTransaksiList;

    private BusinessEntity businessEntity;

    @PostConstruct
    @Override
    protected void init() {
        String username = securityContext.getCallerPrincipal().getName();
        businessEntity = entityFacade.getByAppUsername(username);
        super.init();

        periodList.setSelectionMode(() -> SINGLE);
        periodList.getSelector().setSelectionsLabel("ps");
        periodList.getFilter().setStaticFilter(this::periodFilters);
        
        jenisTransaksiList.setFilterIn(jenis -> (jenis.equals(JenisTransaksi.PENERIMAAN) || jenis.equals(JenisTransaksi.PENGELUARAN)));
        
        filter = new Filter(this::onFilter);
        filter.setContent(filterContent);
    }

    public void postInit() {
        if (periodList.getSelection() == null) {
            AccountingPeriod currentPeriod = periodFacade.getCurrentPeriod(businessEntity);
            periodList.setSelection(currentPeriod);
        }
    }

    public void onFilter(Object obj) {
        
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

    @Override
    protected ReportingJob prepareReportingJob() {

        Map<String, Object> queryParameters = parameters();

        List<ProgramKerja> listProgramKerja = dao.findAll(
                queryParameters, 
                filter.getValues(),
                List.of(SorterData.by("id"))
        );

        Map<String, Object> parameters = new HashMap<>();
        
        parameters.put("parentEntitas", "Yayasan Rumpun Diponegoro");
        parameters.put("entitas", businessEntity.getOrganization().getName());
        parameters.put("periode", periodList.getSelection().getName());
        byte[] parentLogo = organizationFacade.getLogo(businessEntity.getParent().getOrganization().getId());
        parameters.put("parentLogo", parentLogo != null ? new ByteArrayInputStream(parentLogo) : null);
        byte[] logo = organizationFacade.getLogo(businessEntity.getOrganization().getId());
        parameters.put("logo", logo != null ? new ByteArrayInputStream(logo) : null);
        
        parameters.put("judulLaporan", "Laporan Realisasi " + filter.<ProgramKerjaFilter>getContent().getJenisTransaksi().getLabel());
        
        String tanggalLaporan = LocalDate.now()
                .format(DateTimeFormatter.ofPattern("EEEE d MMM yyyy", new Locale("ID")));
        parameters.put("tanggalLaporan", tanggalLaporan);
        parameters.put("lokasi", "Semarang");
        parameters.put("ketua", "Nana Patriatna, S.Sos");

        return new ReportingJob(listProgramKerja, parameters,"ProgramKerja");
    }

    public void onChangePeriod(AjaxBehaviorEvent evt) {
    }

    public AccountingPeriodList getPeriodList() {
        return periodList;
    }

    public JenisTransaksiList getJenisTransaksiList() {
        return jenisTransaksiList;
    }

    public Filter getFilter() {
        return filter;
    }

    public List<Integer> getYearList() {
        return periodFacade.getAvailableYears(businessEntity);
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

}
