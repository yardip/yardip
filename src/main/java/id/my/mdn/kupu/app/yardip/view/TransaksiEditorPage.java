/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.yardip.view;

import id.my.mdn.kupu.app.yardip.dao.KasFacade;
import id.my.mdn.kupu.app.yardip.dao.PosTransaksiFacade;
import id.my.mdn.kupu.app.yardip.dao.TransaksiDetailFacade;
import id.my.mdn.kupu.app.yardip.dao.TransaksiFacade;
import id.my.mdn.kupu.app.yardip.model.BuktiKas;
import id.my.mdn.kupu.app.yardip.model.BuktiKasId;
import id.my.mdn.kupu.app.yardip.model.JenisTransaksi;
import id.my.mdn.kupu.app.yardip.model.PosTransaksi;
import id.my.mdn.kupu.app.yardip.model.StatusMutasiKas;
import id.my.mdn.kupu.app.yardip.model.Transaksi;
import id.my.mdn.kupu.app.yardip.model.TransaksiDetail;
import id.my.mdn.kupu.app.yardip.model.TransaksiDetailId;
import id.my.mdn.kupu.app.yardip.service.TransaksiService;
import id.my.mdn.kupu.app.yardip.view.widget.AltPosTransaksiList;
import id.my.mdn.kupu.core.accounting.entity.AccountingPeriod;
import id.my.mdn.kupu.core.base.dao.AbstractFacade.DefaultChecker;
import id.my.mdn.kupu.core.base.util.FilterTypes.FilterData;
import id.my.mdn.kupu.core.base.util.Result;
import id.my.mdn.kupu.core.base.view.FormPage;
import id.my.mdn.kupu.core.base.view.annotation.Bookmark;
import id.my.mdn.kupu.core.base.view.widget.AbstractValueList;
import id.my.mdn.kupu.core.base.view.widget.IValueList;
import id.my.mdn.kupu.core.base.view.widget.IValueList.SorterData;
import id.my.mdn.kupu.core.base.view.widget.Toast;
import id.my.mdn.kupu.core.party.dao.BusinessEntityFacade;
import id.my.mdn.kupu.core.party.entity.BusinessEntity;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ActionEvent;
import jakarta.faces.event.AjaxBehaviorEvent;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.security.enterprise.SecurityContext;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import org.primefaces.event.FilesUploadEvent;
import org.primefaces.model.file.UploadedFile;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Named(value = "transaksiEditorPage")
@ViewScoped
public class TransaksiEditorPage extends FormPage<Transaksi> {

    @Inject
    private TransaksiFacade dao;

    @Inject
    private BusinessEntityFacade entityFacade;

    @Inject
    private KasFacade kasFacade;

    @Inject
    private AltPosTransaksiList posList;

    private BusinessEntity businessEntity;

    private JenisTransaksi jenisTransaksi;

    private List<TransaksiDetail> detail;

    private Boolean copy = false;

    @Inject
    private SecurityContext securityContext;

    @Bookmark
    private AccountingPeriod period;

    public AccountingPeriod getPeriod() {
        return period;
    }

    public void setPeriod(AccountingPeriod period) {
        this.period = period;
    }

    @Override
    public void load() {
        String username = securityContext.getCallerPrincipal().getName();
        businessEntity = entityFacade.getByAppUsername(username);

        super.load();

        posList.getFilter().setStaticFilter(this::getPosTransaksiFilters);
    }
    
    private List<FilterData> getPosTransaksiFilters() {
        return List.of(
                new FilterData("entity", businessEntity),
                new FilterData("trxType", jenisTransaksi),
                new FilterData("date", LocalDate.now())
        );
    }

    @Override
    protected Transaksi newEntity() {

        Transaksi trx = new Transaksi();
        trx.setBusinessEntity(businessEntity);

        LocalDate now = LocalDate.now();
        if ((now.isEqual(period.getFromDate()) || now.isAfter(period.getFromDate()))
                && (now.isEqual(period.getThruDate()) || now.isBefore(period.getThruDate()))) {
            trx.setTrxDate(now);
        } else {
            trx.setTrxDate(period.getFromDate());
        }

        trx.setAmount(BigDecimal.ZERO);
        trx.setTrxDocs(new ArrayList<>());

        detail = kasFacade.findAll(
                List.of(new FilterData("owner", businessEntity)),
                List.of(new SorterData("name"))
        ).stream().map(kas -> new TransaksiDetail(trx, kas, BigDecimal.ZERO))
                .collect(Collectors.toList());

        return trx;
    }

    @Inject
    private TransaksiDetailFacade detailFacade;

    @Override
    protected void loadEntity() {

        if (copy.equals(Boolean.TRUE)) {
            entity = dao.copyFrom(entity);
            detail = entity.getDetailTransaksi();
            setCreateNew(true);
        } else {
            List<TransaksiDetail> listDetail = detailFacade.findAll(List.of(new FilterData("transaksi", entity)));

            detail = kasFacade.findAll(
                    List.of(new FilterData("owner", businessEntity)),
                    List.of(new SorterData("name"))
            ).stream().map(kas -> {
                TransaksiDetailId id = new TransaksiDetailId(entity.getId(), kas.getId());
                TransaksiDetail tmp = new TransaksiDetail(id);
                int idxDetail = listDetail.indexOf(tmp);
                TransaksiDetail detail = idxDetail > -1
                        ? listDetail.get(idxDetail)
                        : new TransaksiDetail(entity, kas, BigDecimal.ZERO);
                return detail;
            })
                    .collect(Collectors.toList());

            entity.setDetailTransaksi(detail);
        }

        if (entity.getTrxType() != null && entity.getTrxType().getTrxType().equals(JenisTransaksi.MUTASI_KAS)) {
            for (int i = 0; i < detail.size(); i++) {
                if (detail.get(i).getAmount().compareTo(BigDecimal.ZERO) == -1) {
                    detail.get(i).setStatusMutasi(StatusMutasiKas.SOURCE);
                } else if (detail.get(i).getAmount().compareTo(BigDecimal.ZERO) == 1) {
                    detail.get(i).setStatusMutasi(StatusMutasiKas.DESTINATION);
                }
            }
        }

        jenisTransaksi = entity.getTrxType().getTrxType();
    }

    @Override
    public void save(ActionEvent evt) {
        Result<String> saveResult = save();
        if (saveResult.isSuccess()) {
            createNew = false;
            Toast.info(saveResult.payload);
        } else {
            Toast.error(saveResult.payload);
        }
    }

    @Inject
    private TransaksiService trxService;

    @Override
    protected Result<String> checkFormValidity() {
        return super.checkFormValidity();
    }

    @Override
    protected Result<String> save(Transaksi entity) {
        List<TransaksiDetail> validDetail = detail.stream()
                .filter(data -> data.getTmpAmount().compareTo(BigDecimal.ZERO) > 0)
                .map(data -> {
                    data.setTransaksi(entity);
                    return data;
                })
                .collect(Collectors.toList());
        if (!validDetail.isEmpty()) {
            entity.setDetailTransaksi(validDetail);
        }
        return trxService.create(entity);
    }

    @Override
    protected Result<String> edit(Transaksi entity) {
        List<TransaksiDetail> validDetail = detail.stream()
                .map(data -> {
                    data.setTransaksi(entity);
                    return data;
                })
                .collect(Collectors.toList());
        
        entity.setDetailTransaksi(validDetail);
        
        return trxService.edit(entity);
    }

    @Inject
    private PosTransaksiFacade posTrxFacade;

    public void onChangeJenis(AjaxBehaviorEvent evt) {

        if (jenisTransaksi.equals(JenisTransaksi.TRANSFER_SALDO)) {
            PosTransaksi posTransferSaldo = posTrxFacade.findSingleByAttributes(getPosTransaksiFilters());
            entity.setTrxDate(period.getFromDate());
            entity.setTrxType(posTransferSaldo);
            entity.setUraian(posTransferSaldo.getUraian());
        }

        if (jenisTransaksi.equals(JenisTransaksi.MUTASI_KAS)) {
            PosTransaksi posMutasi = posTrxFacade.findSingleByAttributes(getPosTransaksiFilters());
            entity.setTrxType(posMutasi);
            entity.setUraian(posMutasi.getUraian());
        }

        posList.invalidate();
    }

    private final IValueList<BuktiKas> buktiKasList = new AbstractValueList<>() {
        @Override
        protected List<BuktiKas> getFetchedItemsInternal(
                Map<String, Object> parameters, List<FilterData> filters,
                List<SorterData> sorters, DefaultList<BuktiKas> defaultList,
                DefaultChecker defaultChecker) {
            return entity.getTrxDocs();
        }

        @Override
        public String getName() {
            return "buktiKasTbl";
        }
    };

    public IValueList<BuktiKas> getBuktiKasList() {
        return buktiKasList;
    }

    public void onUploadBuktiKas(FilesUploadEvent event) {
        int i = 0;
        if (!entity.getTrxDocs().isEmpty()) {
            BuktiKas lastDoc = entity.getTrxDocs().get(entity.getTrxDocs().size() - 1);
            i = lastDoc.getId().getSeq();
        }
        for (UploadedFile f : event.getFiles().getFiles()) {
            BuktiKas buktiKas = new BuktiKas();
            buktiKas.setId(new BuktiKasId(UUID.randomUUID().toString(), ++i));
            buktiKas.setTransaksi(entity);
            buktiKas.setName(f.getFileName());
            buktiKas.setContent(f.getContent());
            buktiKas.setContentType(f.getContentType());
            buktiKas.setContentSize(f.getSize() / 1000);

            entity.getTrxDocs().add(buktiKas);

            FacesMessage message = new FacesMessage("Successful", f.getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

    public JenisTransaksi getJenisTransaksi() {
        return jenisTransaksi;
    }

    public void setJenisTransaksi(JenisTransaksi jenisTransaksi) {
        this.jenisTransaksi = jenisTransaksi;
    }

    public AltPosTransaksiList getPosList() {
        return posList;
    }

    public List<TransaksiDetail> getDetail() {
        return detail;
    }

    public Boolean getCopy() {
        return copy;
    }

    public void setCopy(Boolean copy) {
        this.copy = copy;
    }

}
