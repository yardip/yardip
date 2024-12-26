/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.core.reporting.view;

import id.my.mdn.kupu.core.reporting.exception.ReportCompilationException;
import id.my.mdn.kupu.core.reporting.exception.ReportDeserializationException;
import id.my.mdn.kupu.core.reporting.exception.ReportFillingException;
import id.my.mdn.kupu.core.reporting.jasperreports.ReportLoadingException;
import id.my.mdn.kupu.core.reporting.model.ReportingJob;
import id.my.mdn.kupu.core.reporting.service.ReportingJobQueue;
import id.my.mdn.kupu.core.reporting.util.Reporter;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author aphasan
 */
@Named(value = "reportStreamer")
@RequestScoped
public class ReportStreamer implements Serializable {

    @Inject
    private Reporter reporter;

    @Inject
    private ReportingJobQueue jobQueue;

    private StreamedContent getContent(String format) {
        

        ReportingJob job = jobQueue.get();
        
        if(job == null) return null;
        
        return DefaultStreamedContent.builder()
                .name(job.getTemplateName() + "." + format)
                .contentType("application/" + format)
                .writer((os) -> {
                    try {
                        reporter.generateReport(job, os, format);
                    } catch (ReportLoadingException
                            | ReportCompilationException
                            | ReportDeserializationException
                            | ReportFillingException ex) {

                        Logger.getLogger(ReportStreamer.class.getName())
                                .log(Level.SEVERE, null, ex);
                    }
                }).build();
    }
    
    public StreamedContent getPdf() {        
        return getContent("pdf");
    }
    
    public StreamedContent getXls() {
        return getContent("xls");
    }

}
