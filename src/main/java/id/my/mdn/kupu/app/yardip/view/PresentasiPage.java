/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.yardip.view;

import id.my.mdn.kupu.core.base.view.Page;
import id.my.mdn.kupu.core.party.dao.BusinessEntityFacade;
import id.my.mdn.kupu.core.party.entity.BusinessEntity;
import id.my.mdn.kupu.core.party.view.widget.BusinessEntityList;
import jakarta.annotation.PostConstruct;
import jakarta.faces.event.AjaxBehaviorEvent;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.security.enterprise.SecurityContext;
import java.io.Serializable;

/**
 *
 * @author aphasan
 */
@Named(value = "presentasiPage")
@ViewScoped
public class PresentasiPage extends Page implements Serializable {

    @Inject
    private SecurityContext securityContext;

    @Inject
    private BusinessEntityFacade entityFacade;

    @Inject
    private BusinessEntityList businessEntityList;

    private BusinessEntity businessEntity;
    
    @PostConstruct
    @Override
    protected void init() {
        String username = securityContext.getCallerPrincipal().getName();
        businessEntity = entityFacade.getByAppUsername(username);
        super.init();
    }

    public void onChangeBusinessEntity(AjaxBehaviorEvent evt) {
    }

    public BusinessEntity getBusinessEntity() {
        return businessEntity;
    }

    public void setBusinessEntity(BusinessEntity businessEntity) {
        this.businessEntity = businessEntity;
    }

    public BusinessEntityList getBusinessEntityList() {
        return businessEntityList;
    }
    
}
