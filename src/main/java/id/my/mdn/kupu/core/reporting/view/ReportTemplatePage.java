/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.core.reporting.view;

import id.my.mdn.kupu.core.base.view.ChildPage;
import id.my.mdn.kupu.core.base.view.Confirmation;
import id.my.mdn.kupu.core.base.view.Page;
import id.my.mdn.kupu.core.base.view.annotation.Bookmarked;
import id.my.mdn.kupu.core.base.view.annotation.Creator;
import id.my.mdn.kupu.core.base.view.annotation.Deleter;
import id.my.mdn.kupu.core.base.view.annotation.Editor;
import id.my.mdn.kupu.core.base.view.annotation.OnInit;
import id.my.mdn.kupu.core.base.view.annotation.OnReload;
import java.io.Serializable;
import jakarta.faces.event.ActionEvent;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 *
 * @author aphasan
 */
@Named(value = "reportTemplatePage")
@ViewScoped
public class ReportTemplatePage extends ChildPage implements Serializable {

    private static final int DELETE_TEMPLATE = 1;

    @Inject
    @Bookmarked    
    private ReportTemplateList dataView;
    
    @OnInit
    public void init() {
//        dataView.init();
    }
    
    @OnReload
    public void reload() {
//        dataView.reload();
    }

    @Override
    public Page onReturns(int what, Object returns) {
        switch (what) {
            case DELETE_TEMPLATE:
                if (((Boolean) returns) == true) {
                    dataView.deleteInternal(dataView.getSelector().getSelection());
                }
                break;
        }
        return super.onReturns(what, returns);
    }
    
    @Creator(of = "dataView")
    public void openDataCreator() {
        gotoChild(ReportTemplateEditorPage.class)
                .open();
    } 
    
    @Editor(of = "dataView")
    public void openDataEditor() {
        gotoChild(ReportTemplateEditorPage.class)
                .addParam("entity")
                .withValues(dataView.getSelector().getSelection())
                .open();
    }

    @Deleter(of = "dataView")
    public void openDataDeleter() {
        Confirmation.from(this).on(DELETE_TEMPLATE)
                .withMessage("Are you sure to delete \"{0}\" ?")
                .andMessageParams(dataView.getSelector().getSelection().getName())
                .open();
    }
    
    public void openReportViewer(ActionEvent evt) {
        gotoChild(ReportViewerPage.class)
                .open();
    }

    public ReportTemplateList getDataView() {
        return dataView;
    }

    public void setDataView(ReportTemplateList dataView) {
        this.dataView = dataView;
    }
    
}
