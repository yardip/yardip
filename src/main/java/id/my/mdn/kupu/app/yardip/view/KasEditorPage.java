/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.yardip.view;

import id.my.mdn.kupu.app.yardip.dao.KasFacade;
import id.my.mdn.kupu.app.yardip.model.Kas;
import id.my.mdn.kupu.core.base.util.Result;
import id.my.mdn.kupu.core.base.view.FormPage;
import id.my.mdn.kupu.core.party.dao.BusinessEntityFacade;
import id.my.mdn.kupu.core.party.entity.BusinessEntity;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.security.enterprise.SecurityContext;
import java.time.LocalDate;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Named(value = "kasEditorPage")
@ViewScoped
public class KasEditorPage extends FormPage<Kas> {

    @Inject
    private KasFacade dao;

    @Inject
    private BusinessEntityFacade entityFacade;

    private BusinessEntity businessEntity;
    
    @Inject
    private SecurityContext securityContext;

    @Override
    public void load() {
        String username = securityContext.getCallerPrincipal().getName();        
        businessEntity = entityFacade.getByAppUsername(username);
        super.load();
    }

    @Override
    protected Kas newEntity() {
        Kas kas = new Kas();
        kas.setOwner(businessEntity);
        kas.setFromDate(LocalDate.now());
        
        return kas;
    }

    @Override
    protected Result<String> save(Kas entity) {
        return dao.create(entity);
    }

    @Override
    protected Result<String> edit(Kas entity) {
        return dao.edit(entity);
    }

}
