/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.core.reporting.model;

import id.my.mdn.kupu.core.reporting.ds.BeanCollectionDataSource;
import id.my.mdn.kupu.core.reporting.ds.ReportDataSource;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
public class ReportingJob {
    
    private ReportingJob parent;
    
    private final String templateName;
    
    private final Map<String, Object> parameters;
    
    private final ReportDataSource dataSource;
    
    private final String[] subReportsTemplateName;

    public ReportingJob(ReportDataSource dataSource, Map<String, Object> parameters, 
            String templateName,  String... subReportTemplateName) {
        this.templateName = templateName;
        this.parameters = parameters;
        this.dataSource = dataSource;
        this.subReportsTemplateName = subReportTemplateName;
    }
    
    public ReportingJob(List data, Map<String, Object> parameters, 
            String templateName,  String... subReportTemplateName) {
        this(new BeanCollectionDataSource(data), parameters, 
                templateName,  subReportTemplateName);
    }

    public ReportingJob getParent() {
        return parent;
    }

    public String getTemplateName() {
        return templateName;
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }

    public ReportDataSource getDataSource() {
        return dataSource;
    }

    public String[] getSubReportsTemplateName() {
        return subReportsTemplateName;
    }
}
