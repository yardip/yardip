/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.yardip.view;

import id.my.mdn.kupu.app.yardip.dao.KasFacade;
import id.my.mdn.kupu.app.yardip.dao.RangkumanTransaksiFacade;
import id.my.mdn.kupu.app.yardip.entity.JenisLaporanTransaksi;
import id.my.mdn.kupu.app.yardip.entity.PeriodFlag;
import id.my.mdn.kupu.app.yardip.entity.RangkumanTransaksi;
import id.my.mdn.kupu.app.yardip.entity.SaldoKas;
import id.my.mdn.kupu.core.accounting.dao.AccountingPeriodFacade;
import id.my.mdn.kupu.core.accounting.entity.AccountingPeriod;
import id.my.mdn.kupu.core.accounting.view.widget.AccountingPeriodList;
import id.my.mdn.kupu.core.base.util.FilterTypes.FilterData;
import id.my.mdn.kupu.core.base.view.annotation.Bookmarked;
import id.my.mdn.kupu.core.base.view.widget.IValueList.SorterData;
import id.my.mdn.kupu.core.base.view.widget.Selector;
import id.my.mdn.kupu.core.hr.dao.EmploymentFacade;
import id.my.mdn.kupu.core.hr.dao.PositionFacade;
import id.my.mdn.kupu.core.hr.entity.Employment;
import id.my.mdn.kupu.core.hr.entity.Position;
import id.my.mdn.kupu.core.hr.view.widget.PositionList;
import id.my.mdn.kupu.core.party.dao.BusinessEntityFacade;
import id.my.mdn.kupu.core.party.dao.OrganizationFacade;
import id.my.mdn.kupu.core.party.dao.PartyContactMechanismFacade;
import id.my.mdn.kupu.core.party.entity.BusinessEntity;
import id.my.mdn.kupu.core.party.entity.ContactType;
import id.my.mdn.kupu.core.party.entity.PartyContactMechanism;
import id.my.mdn.kupu.core.party.entity.PostalAddress;
import id.my.mdn.kupu.core.party.view.widget.BusinessEntityList;
import id.my.mdn.kupu.core.reporting.model.ReportingJob;
import id.my.mdn.kupu.core.reporting.util.Reporter;
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
import java.util.stream.Collectors;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Named(value = "transaksiReportPage")
@ViewScoped
public class TransaksiReportPage extends ReportingChildPage implements Serializable {

    @Inject
    private RangkumanTransaksiFacade dao;

    @Inject
    private KasFacade kasFacade;

    @Inject
    private BusinessEntityFacade entityFacade;

    @Inject
    private OrganizationFacade organizationFacade;

    @Inject
    private AccountingPeriodFacade periodFacade;

    @Bookmarked
    @Inject
    private AccountingPeriodList periodList;

    @Inject
    private SecurityContext securityContext;

    private BusinessEntity businessEntity;

    @Inject
    private BusinessEntityList businessEntityList;

    @Inject
    private PositionFacade positionFacade;

    @Inject
    private Reporter reporter;

    private JenisLaporanTransaksi jenisLaporan = JenisLaporanTransaksi.RUTIN;

    @Inject
    private PositionList positionList;

    @Inject
    private PartyContactMechanismFacade partyContactMechanismFacade;

    @Inject
    private EmploymentFacade employmentFacade;

    @Bookmarked(name = "yr")
    private Integer year = LocalDate.now().getYear();

    @PostConstruct
    @Override
    protected void init() {
        String username = securityContext.getCallerPrincipal().getName();
        businessEntity = entityFacade.getByAppUsername(username);
        super.init();

        periodList.setSelectionMode(() -> Selector.SINGLE);
        periodList.getSelector().setSelectionsLabel("ps");
        periodList.getFilter().setStaticFilter(this::periodFilters);
        
        positionList.setSelectionMode(() -> Selector.SINGLE);
        positionList.getFilter().setStaticFilter(this::positionFilters);
    }

    public void postInit() {
        if (periodList.getSelection() == null) {
            AccountingPeriod currentPeriod = periodFacade.getCurrentPeriod(businessEntity);
            periodList.getSelector().setSelectionInternal(currentPeriod);
        }
    }

    private List<FilterData> periodFilters() {
        return List.of(FilterData.by("businessEntity", businessEntity),
                FilterData.by("year", year != null ? year : 3000),
                FilterData.by("flag", PeriodFlag.BULANAN)
        );
    }

    private List<FilterData> positionFilters() {
        return List.of(FilterData.by("businessEntity", businessEntity)
        );
    }

    private Map<String, Object> parameters() {

        AccountingPeriod periodSelection = periodList.getSelection();

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("entity", businessEntity);
        parameters.put("period", periodSelection);
        return parameters;
    }

    public List<Integer> getYearList() {
        return periodFacade.getAvailableYears(businessEntity);
    }

    public PositionList getPositionList() {
        return positionList;
    }

    private Position contextPosition;

    public Position getContextPosition() {
        return contextPosition;
    }

    public void setContextPosition(Position contextPosition) {
        this.contextPosition = contextPosition;
    }

    private String keperluan;

    public JenisLaporanTransaksi getJenisLaporan() {
        return jenisLaporan;
    }

    public void setJenisLaporan(JenisLaporanTransaksi jenisLaporan) {
        this.jenisLaporan = jenisLaporan;
    }

    public String getKeperluan() {
        return keperluan;
    }

    public void setKeperluan(String keperluan) {
        this.keperluan = keperluan;
    }

    @Override
    protected ReportingJob prepareReportingJob() {

        Map<String, Object> queryParameters = parameters();

        List<String> listKasName = kasFacade.findDistinctKasByName(businessEntity, periodList.getSelection());

        List<RangkumanTransaksi> listTransaksi = dao.findAll(
                () -> dao.generateQuery(listKasName, null),
                0, 0, queryParameters, null,
                List.of(SorterData.by("trxdate"), SorterData.by("created")),
                null, null
        ).stream().map(RangkumanTransaksi::new)
                .collect(Collectors.toList());

        Map<String, Object> parameters = new HashMap<>();

        parameters.put("entitas", businessEntity.getOrganization().getName());
        parameters.put("periode", "Periode " + periodList.getSelection().getName());
        byte[] logo = organizationFacade.getLogo(businessEntity.getOrganization().getId());
        parameters.put("logo", logo != null ? new ByteArrayInputStream(logo) : null);

        String tanggalLaporan = LocalDate.now()
                .format(DateTimeFormatter.ofPattern("EEEE d MMM yyyy", new Locale("ID")));
        String keperluanLaporan = (keperluan == null || keperluan.isBlank()) ? "" : (" " + keperluan);

        parameters.put("uraian", keperluanLaporan);

        for (int i = 0; i < listKasName.size(); i++) {
            parameters.put("kas" + i, listKasName.get(i));
        }

        List<SaldoKas> saldoKas = dao.calculateSaldoKas(businessEntity, periodList.getSelection());

        parameters.put("SaldoKasData", new JRBeanCollectionDataSource(saldoKas));
        parameters.put("periodeAkhir", tanggalLaporan);

        PartyContactMechanism partyPostalAddress
                = partyContactMechanismFacade.findDefaultContactMechanism(
                        businessEntity.getOrganization(),
                        ContactType.PostalAddress);
        String city = "";
        if (partyPostalAddress != null) {
            PostalAddress postalAddress = (PostalAddress) partyPostalAddress.getContactMechanism();
            if (postalAddress != null && postalAddress.getDistrict() != null) {
                city = postalAddress.getDistrict().getName();
            }
        }

        parameters.put("lokasi", city);

        parameters.put("Pengesahan", reporter.getTemplate(jenisLaporan.getLabel().replaceAll("\\s+", "")));
        parameters.put("PengesahanData", new JREmptyDataSource());

        switch (jenisLaporan) {
            case RUTIN:
                setPengesahanRutin(parameters);
                break;
            case SERAH_TERIMA:
                setPengesahanSerahTerima(parameters);
                break;
            default:
                break;
        }

        return new ReportingJob(
                listTransaksi,
                parameters,
                "BukuKas" + listKasName.size(),
                "SaldoKas"
        );
    }

    private void setPengesahanRutin(Map<String, Object> parameters) {

        Position signerPosition1 = positionFacade.findSingleByAttributes(List.of(
                FilterData.by("businessEntity", businessEntity),
                FilterData.by("top", null)
        ));

        Employment signerEmployment1 = employmentFacade.getExclusiveEmployment(signerPosition1, LocalDate.now());

        Position signerPosition2 = positionFacade.findSingleByAttributes(List.of(
                FilterData.by("businessEntity", businessEntity),
                FilterData.by("name", "Bendahara")
        ));

        Employment signerEmployment2 = employmentFacade.getExclusiveEmployment(signerPosition2, LocalDate.now());

        parameters.put("signerPosition1", signerPosition1 != null ? signerPosition1.getName() : "");
        parameters.put("signerEmployment1", signerEmployment1 != null ? signerEmployment1.getEmployee().getPerson().getName() : "");

        parameters.put("signerPosition2", signerPosition2 != null ? signerPosition2.getName() : "");
        parameters.put("signerEmployment2", signerEmployment2 != null ? signerEmployment2.getEmployee().getPerson().getName() : "");

        parameters.put(
                "uraian",
                String.format(
                        "Pada hari ini, %s, Buku Kas Bank ditutup untuk keperluan Rutin%s, dengan saldo penutupan sebagai berikut;",
                        parameters.get("periodeAkhir"),
                        parameters.get("uraian")
                )
        );
    }

    private void setPengesahanSerahTerima(Map<String, Object> parameters) {

        Position position = positionFacade.findSingleByAttributes(List.of(
                FilterData.by("businessEntity", businessEntity),
                FilterData.by("name", positionList.getSelection().getName())
        ));

        Employment signerEmployment2 = employmentFacade.getExclusiveEmployment(position, LocalDate.now());

        parameters.put("signerEmployment2", signerEmployment2 != null ? signerEmployment2.getEmployee().getPerson().getName() : "");

        Employment signerEmployment1 = employmentFacade.getExclusiveEmployment(position, signerEmployment2.getFromDate().minusDays(1));

        parameters.put("signerEmployment1", signerEmployment1 != null ? signerEmployment1.getEmployee().getPerson().getName() : "");

        Position signerPosition3 = positionFacade.findSingleByAttributes(List.of(
                FilterData.by("businessEntity", businessEntity),
                FilterData.by("name", "Bendahara")
        ));

        Employment signerEmployment3 = employmentFacade.getExclusiveEmployment(signerPosition3, LocalDate.now());

        parameters.put("signerPosition3", signerPosition3 != null ? signerPosition3.getName() : "");
        parameters.put("signerEmployment3", signerEmployment3 != null ? signerEmployment3.getEmployee().getPerson().getName() : "");

        parameters.put(
                "uraian",
                String.format(
                        "Pada hari ini, %s, Buku Kas Bank ditutup untuk keperluan Serah Terima Jabatan %s %s%s, dengan saldo penutupan sebagai berikut;",
                        parameters.get("periodeAkhir"),
                        positionList.getSelection().getName(),
                        businessEntity.getOrganization().getName(),
                        parameters.get("uraian")
                )
        );
    }

    public void onChangeBusinessEntity(AjaxBehaviorEvent evt) {
        periodList.reset();
        positionList.reset();
        postInit();
    }

    public void onChangeYear(AjaxBehaviorEvent evt) {
        periodList.reset();
    }

    public void onChangePeriod(AjaxBehaviorEvent evt) {
    }

    public AccountingPeriodList getPeriodList() {
        return periodList;
    }

    public BusinessEntityList getBusinessEntityList() {
        return businessEntityList;
    }

    public void setBusinessEntity(BusinessEntity businessEntity) {
        this.businessEntity = businessEntity;
    }

    public BusinessEntity getBusinessEntity() {
        return businessEntity;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

}
