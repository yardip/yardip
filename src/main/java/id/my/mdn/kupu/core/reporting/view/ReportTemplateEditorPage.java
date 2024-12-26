/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.core.reporting.view;

import id.my.mdn.kupu.core.base.util.Result;
import id.my.mdn.kupu.core.base.view.FormPage;
import id.my.mdn.kupu.core.reporting.dao.ReportTemplateFacade;
import id.my.mdn.kupu.core.reporting.model.ReportTemplate;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 *
 * @author aphasan
 */
@Named(value = "reportTemplateEditorPage")
@ViewScoped
public class ReportTemplateEditorPage extends FormPage<ReportTemplate> {
    
    @Inject
    private ReportTemplateFacade dao;

    @Override
    protected ReportTemplate newEntity() {
        return new ReportTemplate();
    }

    @Override
    protected Result<String> save(ReportTemplate entity) {
        return dao.create(entity);
    }

    @Override
    protected Result<String> edit(ReportTemplate entity) {
        entity.setCompiledTemplate(new byte[]{});
        return dao.edit(entity);
    }
    
}
