/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.reporting.view.converter;

import id.my.mdn.kupu.core.reporting.dao.ReportTemplateFacade;
import id.my.mdn.kupu.core.reporting.model.ReportTemplate;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 *
 * @author aphasan
 */
@Named("ReportTemplateConverter")
@FacesConverter(value = "ReportTemplateConverter", managed = true)
public class ReportTemplateConverter implements Converter<ReportTemplate> {
    
    @Inject
    private ReportTemplateFacade dao;

    @Override
    public ReportTemplate getAsObject(FacesContext context, UIComponent component, String value) {
        return dao.find(value);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, ReportTemplate value) {
        return value.getId();
    }
    
}
