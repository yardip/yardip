/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.yardip.view;

import id.my.mdn.kupu.app.yardip.entity.BuktiKas;
import id.my.mdn.kupu.app.yardip.entity.BuktiKasId;
import id.my.mdn.kupu.app.yardip.entity.Transaksi;
import id.my.mdn.kupu.app.yardip.view.widget.BuktiKasList;
import id.my.mdn.kupu.core.base.util.FilterTypes.FilterData;
import id.my.mdn.kupu.core.base.view.ChildPage;
import id.my.mdn.kupu.core.base.view.annotation.Bookmarked;
import id.my.mdn.kupu.core.base.view.annotation.Deleter;
import id.my.mdn.kupu.core.base.view.widget.Selector;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import org.primefaces.event.FilesUploadEvent;
import org.primefaces.model.file.UploadedFile;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Named(value = "buktiKasPage")
@ViewScoped
public class BuktiKasPage extends ChildPage implements Serializable {

    @Bookmarked
    @Inject
    private BuktiKasList masterDataView;

    @Bookmarked(name = "trx")
    private Transaksi transaksi;

    @PostConstruct
    @Override
    protected void init() {
        super.init();

        masterDataView.setSelectionMode(() -> Selector.SINGLE);
        masterDataView.getSelector().setSelectionsLabel("ms");
        masterDataView.getFilter().setStaticFilter(this::buktiKasFilter);
    }

    private List<FilterData> buktiKasFilter() {
        return List.of(new FilterData("transaksi", transaksi));
    }

    @Deleter(of = "masterDataView")
    public void deleteBuktiKas() {
        masterDataView.deleteSelections();
    }

    public void onUploadBuktiKas(FilesUploadEvent event) {
        int i = 0;
        if (!transaksi.getTrxDocs().isEmpty()) {
            BuktiKas lastDoc = transaksi.getTrxDocs().get(transaksi.getTrxDocs().size() - 1);
            i = lastDoc.getId().getSeq();
        }
        for (UploadedFile f : event.getFiles().getFiles()) {
            BuktiKas buktiKas = new BuktiKas();
            buktiKas.setId(new BuktiKasId(UUID.randomUUID().toString(), ++i));
            buktiKas.setTransaksi(transaksi);
            buktiKas.setName(f.getFileName());
            buktiKas.setContent(f.getContent());
            buktiKas.setContentType(f.getContentType());
            buktiKas.setContentSize(f.getSize() / 1000);

            transaksi.getTrxDocs().add(buktiKas);
            
            masterDataView.create(buktiKas);

            FacesMessage message = new FacesMessage("Successful", f.getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

    public BuktiKasList getMasterDataView() {
        return masterDataView;
    }

    public Transaksi getTransaksi() {
        return transaksi;
    }

    public void setTransaksi(Transaksi transaksi) {
        this.transaksi = transaksi;
    }

}
