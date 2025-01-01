/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.yardip.view;

import id.my.mdn.kupu.app.yardip.dao.PosTransaksiFacade;
import id.my.mdn.kupu.app.yardip.model.JenisTransaksi;
import id.my.mdn.kupu.app.yardip.model.PosTransaksi;
import id.my.mdn.kupu.app.yardip.view.widget.JenisTransaksiList;
import id.my.mdn.kupu.app.yardip.view.widget.PosTransaksiList;
import id.my.mdn.kupu.core.base.util.FilterTypes.FilterData;
import id.my.mdn.kupu.core.base.util.Result;
import id.my.mdn.kupu.core.base.view.FormPage;
import id.my.mdn.kupu.core.base.view.annotation.Bookmark;
import id.my.mdn.kupu.core.party.dao.BusinessEntityFacade;
import id.my.mdn.kupu.core.party.entity.BusinessEntity;
import jakarta.faces.event.AjaxBehaviorEvent;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.security.enterprise.SecurityContext;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Named(value = "programKerjaEditorPage")
@ViewScoped
public class ProgramKerjaEditorPage extends FormPage<PosTransaksi> {

    @Inject
    private PosTransaksiFacade dao;

    @Inject
    private BusinessEntityFacade entityFacade;
    
    @Inject
    private JenisTransaksiList jenisTransaksiList;

    @Bookmark(name = "ttrx")
    private JenisTransaksi jenisTransaksi;

    @Inject
    private PosTransaksiList posList;

    private BusinessEntity businessEntity;
    
    @Inject
    private SecurityContext securityContext;

    @Override
    public void load() {
        String username = securityContext.getCallerPrincipal().getName();        
        businessEntity = entityFacade.getByAppUsername(username);
        super.load();

        jenisTransaksiList.setFilterIn(jenis -> (jenis.equals(JenisTransaksi.PENERIMAAN)
                || jenis.equals(JenisTransaksi.PENGELUARAN))
        );
    }

    @Override
    protected PosTransaksi newEntity() {
        PosTransaksi posTransaksi = new PosTransaksi();
        posTransaksi.setEntity(businessEntity);
        posTransaksi.setTrxType(jenisTransaksi);
        posTransaksi.setTarget(BigDecimal.ZERO);
        posTransaksi.setFromDate(
                LocalDate.of(LocalDate.now().getYear(), Month.JANUARY, 1)                
        );
        posTransaksi.setThruDate(
                LocalDate.of(LocalDate.now().getYear(), Month.DECEMBER, 31)                
        );
        
        return posTransaksi;
    }

    @Override
    protected Result<String> save(PosTransaksi entity) {
        return dao.create(entity);
    }

    @Override
    protected Result<String> edit(PosTransaksi entity) {
        return dao.edit(entity);
    }

    public void onChangeJenis(AjaxBehaviorEvent evt) {
        posList.getFilter().setStaticFilter(() -> List.of(
                new FilterData("entity", businessEntity),
                new FilterData("trxType", jenisTransaksi)
        ));
        posList.invalidate();
    }

    public JenisTransaksi getJenisTransaksi() {
        return jenisTransaksi;
    }

    public void setJenisTransaksi(JenisTransaksi jenisTransaksi) {
        this.jenisTransaksi = jenisTransaksi;
    }

    public PosTransaksiList getPosList() {
        return posList;
    }

    public JenisTransaksiList getJenisTransaksiList() {
        return jenisTransaksiList;
    }

}
