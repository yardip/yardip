/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.reporting.view.converter;

import id.my.mdn.kupu.core.base.view.converter.SelectionsConverter;
import id.my.mdn.kupu.core.reporting.dao.ReportTemplateFacade;
import id.my.mdn.kupu.core.reporting.model.ReportTemplate;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 *
 * @author aphasan
 */
@Named("ReportTemplateListConverter")
@FacesConverter(value = "ReportTemplateListConverter", managed = true)
public class ReportTemplateListConverter extends SelectionsConverter<ReportTemplate> {
    
    @Inject
    private ReportTemplateFacade dao;

    @Override
    protected ReportTemplate getAsObject(String value) {
        return dao.find(value);
    }

    @Override
    protected String getAsString(ReportTemplate obj) {
        return obj.getId();
    }
    
}
