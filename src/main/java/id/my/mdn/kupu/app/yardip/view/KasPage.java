/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.yardip.view;

import id.my.mdn.kupu.app.yardip.view.widget.KasList;
import id.my.mdn.kupu.core.base.util.FilterTypes.FilterData;
import id.my.mdn.kupu.core.base.view.Page;
import id.my.mdn.kupu.core.base.view.annotation.Bookmarked;
import id.my.mdn.kupu.core.base.view.annotation.Creator;
import id.my.mdn.kupu.core.base.view.annotation.Deleter;
import id.my.mdn.kupu.core.base.view.annotation.Editor;
import id.my.mdn.kupu.core.base.view.widget.IValueList.SorterData;
import id.my.mdn.kupu.core.party.dao.BusinessEntityFacade;
import id.my.mdn.kupu.core.party.entity.BusinessEntity;
import jakarta.annotation.PostConstruct;
import jakarta.faces.event.AjaxBehaviorEvent;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.security.enterprise.SecurityContext;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Named(value = "kasPage")
@ViewScoped
public class KasPage extends Page implements Serializable {

    @Bookmarked
    @Inject
    private KasList dataView;

    @Inject
    private BusinessEntityFacade entityFacade;

    private BusinessEntity entity;
    
    @Inject
    private SecurityContext securityContext;

    @PostConstruct
    @Override
    protected void init() {
        String username = securityContext.getCallerPrincipal().getName();        
        entity = entityFacade.getByAppUsername(username);
        super.init();
        dataView.getFilter().setStaticFilter(this::filters);
        dataView.getSorters().add(SorterData.by("id"));

    }

    @Creator(of = "dataView")
    public void openKasCreator() {
        gotoChild(KasEditorPage.class)
                .open();
    }

    @Editor(of = "dataView")
    public void openKasEditor() {
        gotoChild(KasEditorPage.class)
                .addParam("entity")
                .withValues(dataView.getSelected())
                .open();
    }

    @Deleter(of = "dataView")
    public void openKasDeleter() {
        dataView.deleteSelections();
    }

    private List<FilterData> filters() {
        return List.of(FilterData.by("entity", entity));
    }

    public void onChangePeriod(AjaxBehaviorEvent evt) {
        dataView.reset();
    }

    public void onChangeJenis(AjaxBehaviorEvent evt) {
        dataView.reset();
    }

    public BusinessEntity getEntity() {
        return entity;
    }

    public void setEntity(BusinessEntity entity) {
        this.entity = entity;
    }

    public KasList getDataView() {
        return dataView;
    }

}
