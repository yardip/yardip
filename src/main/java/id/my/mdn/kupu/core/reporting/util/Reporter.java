/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.reporting.util;

import id.my.mdn.kupu.core.reporting.dao.ReportTemplateFacade;
import id.my.mdn.kupu.core.reporting.ds.ReportDataSource;
import id.my.mdn.kupu.core.reporting.exception.ReportCompilationException;
import id.my.mdn.kupu.core.reporting.exception.ReportDeserializationException;
import id.my.mdn.kupu.core.reporting.exception.ReportFillingException;
import id.my.mdn.kupu.core.reporting.jasperreports.ReportCompiler;
import id.my.mdn.kupu.core.reporting.jasperreports.ReportFiller;
import id.my.mdn.kupu.core.reporting.jasperreports.ReportLoader;
import id.my.mdn.kupu.core.reporting.jasperreports.ReportLoadingException;
import id.my.mdn.kupu.core.reporting.model.ReportTemplate;
import id.my.mdn.kupu.core.reporting.model.ReportingJob;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;

/**
 *
 * @author Arief Prihasanto <ariefp5758 at gmail.com>
 */
@Named(value = "reporter")
@Dependent
public class Reporter implements Serializable {

    public static final String PDF_OUTPUT = "pdf";

    public static final String XLS_OUTPUT = "xls";

    @Inject
    private ReportTemplateFacade reportTemplateFacade;

    @Inject
    private ReportLoader loader;        // Output JasperDesign

    @Inject
    private ReportCompiler compiler;    // Output JasperReport

    @Inject
    private ReportFiller filler;        // Output JasperPrint

    public byte[] compileNSerializeTemplate(ReportTemplate reportTemplate)
            throws ReportLoadingException, ReportCompilationException {
        byte[] compiledTemplate = reportTemplate.getCompiledTemplate();

        if (compiledTemplate == null || compiledTemplate.length == 0) {
            JasperDesign jasperDesign = loader.load(reportTemplate);
            JasperReport jasperReport = compiler.compile(jasperDesign);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos;
            try {
                oos = new ObjectOutputStream(bos);
                oos.writeObject(jasperReport);
                oos.flush();
                compiledTemplate = bos.toByteArray();
                reportTemplate.setCompiledTemplate(compiledTemplate);
                reportTemplateFacade.edit(reportTemplate);
            } catch (IOException ex) {
                Logger.getLogger(Reporter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return compiledTemplate;
    }

    public byte[] compileNSerializeTemplate(String reportTemplateName)
            throws ReportLoadingException, ReportCompilationException {
        byte[] compiledTemplate = null;
        ReportTemplate reportTemplate = reportTemplateFacade.findSingleByAttribute("name", reportTemplateName);
        if (reportTemplate != null) {
            compiledTemplate = compileNSerializeTemplate(reportTemplate);
        }

        return compiledTemplate;
    }

    public JasperReport getTemplate(String name) {
        ReportTemplate reportTemplate = reportTemplateFacade.findSingleByAttribute("name", name);

        if (reportTemplate != null) {
            try {
                byte[] compiledTemplate = reportTemplate.getCompiledTemplate();

                if (compiledTemplate == null || compiledTemplate.length == 0) {
                    JasperDesign jasperDesign = loader.load(reportTemplate);
                    JasperReport jasperReport = compiler.compile(jasperDesign);
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    ObjectOutputStream oos;
                    try {
                        oos = new ObjectOutputStream(bos);
                        oos.writeObject(jasperReport);
                        oos.flush();
                        compiledTemplate = bos.toByteArray();
                        reportTemplate.setCompiledTemplate(compiledTemplate);
                        reportTemplateFacade.edit(reportTemplate);
                        return jasperReport;
                    } catch (IOException ex) {
                        Logger.getLogger(Reporter.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    ByteArrayInputStream bos = new ByteArrayInputStream(compiledTemplate);
                    ObjectInputStream oos;
                    try {
                        oos = new ObjectInputStream(bos);
                        JasperReport jasperReport = (JasperReport) oos.readObject();
                        oos.close();
                        return jasperReport;
                    } catch (IOException | ClassNotFoundException ex) {
                        Logger.getLogger(Reporter.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } catch (Exception ex) {
                Logger.getLogger(Reporter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return null;
    }

    public void compileNSerializeSubTemplates(ReportingJob job) throws ReportLoadingException, ReportCompilationException {
        if (job.getSubReportsTemplateName() != null && job.getSubReportsTemplateName().length > 0) {
            for (String subReportTemplateName : job.getSubReportsTemplateName()) {
                ReportTemplate reportTemplate = reportTemplateFacade.findSingleByAttribute("name", subReportTemplateName);
                if (reportTemplate != null) {
                    byte[] compiledTemplate = reportTemplate.getCompiledTemplate();

                    if (compiledTemplate == null || compiledTemplate.length == 0) {
                        JasperDesign jasperDesign = loader.load(reportTemplate);
                        JasperReport jasperReport = compiler.compile(jasperDesign);
                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        ObjectOutputStream oos;
                        try {
                            oos = new ObjectOutputStream(bos);
                            oos.writeObject(jasperReport);
                            oos.flush();
                            compiledTemplate = bos.toByteArray();
                            reportTemplate.setCompiledTemplate(compiledTemplate);
                            reportTemplateFacade.edit(reportTemplate);
                        } catch (IOException ex) {
                            Logger.getLogger(Reporter.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        job.getParameters().put(subReportTemplateName, jasperReport);
                    } else {
                        ByteArrayInputStream bos = new ByteArrayInputStream(compiledTemplate);
                        ObjectInputStream oos;
                        try {
                            oos = new ObjectInputStream(bos);
                            JasperReport jasperReport = (JasperReport) oos.readObject();
                            oos.close();
                            job.getParameters().put(subReportTemplateName, jasperReport);
                        } catch (IOException | ClassNotFoundException ex) {
                            Logger.getLogger(Reporter.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        }
    }

    private InputStream deserializeTemplate(byte[] compiledTemplate)
            throws ReportDeserializationException {
        ByteArrayInputStream bis = new ByteArrayInputStream(compiledTemplate);
        return bis;
    }

    public void generateReport(ReportingJob job, OutputStream os, String format)
            throws ReportCompilationException, ReportDeserializationException,
            ReportLoadingException, ReportFillingException {
        compileNSerializeSubTemplates(job);
        generateReport(job.getTemplateName(), job.getParameters(), job.getDataSource(), os, format);
    }

    private void generateReport(String templateName, Map<String, Object> parameters,
            ReportDataSource dataSource, OutputStream os, String format)
            throws ReportDeserializationException, ReportLoadingException,
            ReportCompilationException, ReportFillingException {
        byte[] compiledTemplate = compileNSerializeTemplate(templateName);
        generateReport(compiledTemplate, parameters, dataSource, os, format);
    }

    private void generateReport(byte[] compiledTemplate, Map<String, Object> parameters,
            ReportDataSource dataSource, OutputStream os, String format) throws ReportLoadingException,
            ReportCompilationException, ReportDeserializationException,
            ReportFillingException {

        if (compiledTemplate != null) {
            switch (format) {
                case XLS_OUTPUT:
                    generateXlsxReport(compiledTemplate, parameters, dataSource, os);
                    break;
                case PDF_OUTPUT:
                default:
                    generatePdfReport(compiledTemplate, parameters, dataSource, os);
                    break;
            }
        }
    }

    private void generatePdfReport(byte[] compiledTemplate, Map<String, Object> parameters,
            ReportDataSource dataSource, OutputStream os)
            throws ReportLoadingException, ReportCompilationException,
            ReportDeserializationException, ReportFillingException {
        if (compiledTemplate != null) {
            filler.fillAsPdf(deserializeTemplate(compiledTemplate), parameters, dataSource, os);
        }
    }

    private void generateXlsxReport(byte[] compiledTemplate, Map<String, Object> parameters,
            ReportDataSource dataSource, OutputStream os)
            throws ReportLoadingException, ReportCompilationException,
            ReportDeserializationException, ReportFillingException {
        if (compiledTemplate != null) {
            filler.fillAsXlsx(deserializeTemplate(compiledTemplate), parameters, dataSource, os);
        }
    }
}
