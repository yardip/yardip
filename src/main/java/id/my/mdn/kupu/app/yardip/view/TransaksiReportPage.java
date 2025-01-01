/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.yardip.view;

import id.my.mdn.kupu.app.yardip.dao.KasFacade;
import id.my.mdn.kupu.app.yardip.dao.ProgramKerjaFacade;
import id.my.mdn.kupu.app.yardip.dao.ProgresProgramKerjaFacade;
import id.my.mdn.kupu.app.yardip.dao.RangkumanTransaksiFacade;
import id.my.mdn.kupu.app.yardip.model.JenisLaporanTransaksi;
import static id.my.mdn.kupu.app.yardip.model.JenisLaporanTransaksi.PROGRES_PENGELUARAN;
import static id.my.mdn.kupu.app.yardip.model.JenisLaporanTransaksi.REKAP_PENGELUARAN;
import static id.my.mdn.kupu.app.yardip.model.JenisLaporanTransaksi.RUTIN;
import static id.my.mdn.kupu.app.yardip.model.JenisLaporanTransaksi.SERAH_TERIMA;
import id.my.mdn.kupu.app.yardip.model.JenisTransaksi;
import static id.my.mdn.kupu.app.yardip.model.JenisTransaksi.PENERIMAAN;
import static id.my.mdn.kupu.app.yardip.model.JenisTransaksi.PENGELUARAN;
import id.my.mdn.kupu.app.yardip.model.PeriodFlag;
import id.my.mdn.kupu.app.yardip.model.ProgramKerja;
import id.my.mdn.kupu.app.yardip.model.ProgresProgramKerja;
import id.my.mdn.kupu.app.yardip.model.RangkumanTransaksi;
import id.my.mdn.kupu.app.yardip.model.SaldoKas;
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
import id.my.mdn.kupu.core.party.entity.Organization;
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

    @Inject
    private ProgramKerjaFacade programKerjaFacade;

    @Inject
    private ProgresProgramKerjaFacade progresProgramKerjaFacade;

    private String keperluan;

    private Position contextPosition;

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
        return List.of(
                FilterData.by("businessEntity", businessEntity)
        );
    }

    private Map<String, Object> parameters() {

        AccountingPeriod periodSelection = periodList.getSelection();

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("entity", businessEntity);
        parameters.put("period", periodSelection);
        return parameters;
    }

    @Override
    protected ReportingJob prepareReportingJob() {
        AccountingPeriod period = periodList.getSelection();
        switch (jenisLaporan) {
            case RUTIN:
            case SERAH_TERIMA:
                return prepareLaporanTransaksi(businessEntity, jenisLaporan, period);
            case REKAP_PENERIMAAN:
            case REKAP_PENGELUARAN:
                return prepareLaporanArusKas(businessEntity, jenisLaporan, period);
            case PROGRES_PENERIMAAN:
            case PROGRES_PENGELUARAN:
                return prepareLaporanProgram(businessEntity, jenisLaporan, period);
            case REKAPITULASI:
                return prepareLaporanRekapitulasi(businessEntity, period);
            default:
                return null;
        }
    }

    private ReportingJob prepareLaporanArusKas(BusinessEntity entity, JenisLaporanTransaksi jenisLaporan, AccountingPeriod period) {

        JenisTransaksi jenisTransaksi = PENERIMAAN;

        if (jenisLaporan.equals(REKAP_PENGELUARAN)) {
            jenisTransaksi = PENGELUARAN;
        }

        List<ProgresProgramKerja> data = progresProgramKerjaFacade.findAll(
                Map.of(
                        "businessEntity", entity,
                        "period", period,
                        "jenisTransaksi", jenisTransaksi)
        );

        BusinessEntity parentEntity = entity.getParent();

        Position signerPosition1 = positionFacade.findSingleByAttributes(List.of(
                FilterData.by("businessEntity", parentEntity != null ? parentEntity : entity),
                FilterData.by("top", null)
        ));

        Employment ketua = getEmployment(signerPosition1);

        Organization org = parentEntity != null ? parentEntity.getOrganization() : entity.getOrganization();
        byte[] logo = organizationFacade.getLogo(org.getId());

        String tanggalLaporan = LocalDate.now()
                .format(DateTimeFormatter.ofPattern("EEEE d MMM yyyy", new Locale("ID")));

        AccountingPeriod prevPeriode = periodFacade.getPreviousPeriod(businessEntity, period);

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("parentEntitas", parentEntity != null ? parentEntity.getOrganization().getName() : null);
        parameters.put("entitas", entity.getOrganization().getName());
        parameters.put("logo", logo);
        parameters.put("judulLaporan", jenisLaporan.getLabel());
        parameters.put("periode", period.getName());
        parameters.put("prevPeriode", prevPeriode.getName());
        parameters.put("tanggalLaporan", tanggalLaporan);
        parameters.put("lokasi", "Semarang");
        parameters.put("ketua", ketua.getEmployee().getPerson().getName());

        return new ReportingJob(
                data,
                parameters,
                "ArusKas"
        );

    }

    private ReportingJob prepareLaporanRekapitulasi(BusinessEntity entity, AccountingPeriod period) {
        return null;
    }

    private ReportingJob prepareLaporanProgram(BusinessEntity entity, JenisLaporanTransaksi jenisLaporan, AccountingPeriod period) {

        Map<String, Object> queryParameters = parameters();

        JenisTransaksi jenisTransaksi = JenisTransaksi.PENERIMAAN;

        if (jenisLaporan.equals(PROGRES_PENGELUARAN)) {
            jenisTransaksi = JenisTransaksi.PENGELUARAN;
        }

        List<ProgramKerja> listProgramKerja = programKerjaFacade.findAll(
                queryParameters,
                List.of(FilterData.by("jenisTransaksi", jenisTransaksi)),
                List.of(SorterData.by("id"))
        );

        Map<String, Object> parameters = new HashMap<>();

        parameters.put("parentEntitas", "Yayasan Rumpun Diponegoro");
        parameters.put("entitas", entity.getOrganization().getName());
        parameters.put("periode", period.getName());
        byte[] parentLogo = organizationFacade.getLogo(entity.getParent().getOrganization().getId());
        parameters.put("parentLogo", parentLogo != null ? new ByteArrayInputStream(parentLogo) : null);
        byte[] logo = organizationFacade.getLogo(entity.getOrganization().getId());
        parameters.put("logo", logo != null ? new ByteArrayInputStream(logo) : null);

        parameters.put("judulLaporan", "Laporan Realisasi " + jenisTransaksi.getLabel());

        String tanggalLaporan = LocalDate.now()
                .format(DateTimeFormatter.ofPattern("EEEE d MMM yyyy", new Locale("ID")));
        parameters.put("tanggalLaporan", tanggalLaporan);
        parameters.put("lokasi", "Semarang");
        parameters.put("ketua", "Nana Patriatna, S.Sos");

        return new ReportingJob(listProgramKerja, parameters, "ProgramKerja");
    }

    private ReportingJob prepareLaporanTransaksi(BusinessEntity entity, JenisLaporanTransaksi jenisLaporan, AccountingPeriod period) {

        Map<String, Object> queryParameters = parameters();

        List<String> listKasName = kasFacade.findDistinctKasByName(entity, period);

        List<RangkumanTransaksi> listTransaksi = dao.findAll(
                () -> dao.generateQuery(listKasName, null),
                0, 0, queryParameters, null,
                List.of(SorterData.by("trxdate"), SorterData.by("created")),
                null, null
        ).stream().map(RangkumanTransaksi::new)
                .collect(Collectors.toList());

        Map<String, Object> parameters = new HashMap<>();

        parameters.put("entitas", entity.getOrganization().getName());
        parameters.put("periode", "Periode " + period.getName());
        byte[] logo = organizationFacade.getLogo(entity.getOrganization().getId());
        parameters.put("logo", logo != null ? new ByteArrayInputStream(logo) : null);

        String tanggalLaporan = LocalDate.now()
                .format(DateTimeFormatter.ofPattern("EEEE d MMM yyyy", new Locale("ID")));
        String keperluanLaporan = (keperluan == null || keperluan.isBlank()) ? "" : (" " + keperluan);

        parameters.put("uraian", keperluanLaporan);

        for (int i = 0; i < listKasName.size(); i++) {
            parameters.put("kas" + i, listKasName.get(i));
        }

        List<SaldoKas> saldoKas = dao.calculateSaldoKas(entity, period);

        parameters.put("SaldoKasData", new JRBeanCollectionDataSource(saldoKas));
        parameters.put("periodeAkhir", tanggalLaporan);

        PartyContactMechanism partyPostalAddress
                = partyContactMechanismFacade.findDefaultContactMechanism(
                        entity.getOrganization(),
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
            case RUTIN ->
                setPengesahanRutin(parameters);
            case SERAH_TERIMA ->
                setPengesahanSerahTerima(parameters);
            default -> {
                return null;
            }
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

        Employment signerEmployment1 = getEmployment(signerPosition1);

        Position signerPosition2 = positionFacade.findSingleByAttributes(List.of(
                FilterData.by("businessEntity", businessEntity),
                FilterData.by("name", "Bendahara")
        ));

        Employment signerEmployment2 = getEmployment(signerPosition2);

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

    private Employment getEmployment(Position position, LocalDate date) {
        return employmentFacade.getExclusiveEmployment(position, date);
    }

    private Employment getEmployment(Position position) {
        return employmentFacade.getExclusiveEmployment(position, LocalDate.now());
    }

    private void setPengesahanSerahTerima(Map<String, Object> parameters) {

        Position position = positionFacade.findSingleByAttributes(List.of(
                FilterData.by("businessEntity", businessEntity),
                FilterData.by("name", positionList.getSelection().getName())
        ));

        Employment signerEmployment2 = getEmployment(position);

        parameters.put("signerEmployment2", signerEmployment2 != null ? signerEmployment2.getEmployee().getPerson().getName() : "");

        Employment signerEmployment1 = getEmployment(position, signerEmployment2.getFromDate().minusDays(1));

        parameters.put("signerEmployment1", signerEmployment1 != null ? signerEmployment1.getEmployee().getPerson().getName() : "");

        Position signerPosition3 = positionFacade.findSingleByAttributes(List.of(
                FilterData.by("businessEntity", businessEntity),
                FilterData.by("name", "Bendahara")
        ));

        Employment signerEmployment3 = getEmployment(signerPosition3);

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
        AccountingPeriod currentPeriod = periodFacade.getPeriod(
                businessEntity, 
                periodList.getSelection().getFromDate().withYear(year)
        );
        periodList.reset();
        periodList.getSelector().setSelectionInternal(currentPeriod);
        positionList.reset();
        postInit();
    }

    public void onChangeYear(AjaxBehaviorEvent evt) {
        AccountingPeriod currentPeriod = periodFacade.getPeriod(
                businessEntity, 
                periodList.getSelection().getFromDate().withYear(year)
        );
        periodList.reset();
        periodList.getSelector().setSelectionInternal(currentPeriod);
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

    public List<Integer> getYearList() {
        return periodFacade.getAvailableYears(businessEntity);
    }

    public PositionList getPositionList() {
        return positionList;
    }

    public Position getContextPosition() {
        return contextPosition;
    }

    public void setContextPosition(Position contextPosition) {
        this.contextPosition = contextPosition;
    }

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

}
