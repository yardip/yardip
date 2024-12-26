/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.reporting.jasperreports;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import id.my.mdn.kupu.core.reporting.util.ReportSize;
import static id.my.mdn.kupu.core.reporting.util.ReportSize.A0;
import static id.my.mdn.kupu.core.reporting.util.ReportSize.A1;
import static id.my.mdn.kupu.core.reporting.util.ReportSize.A2;
import static id.my.mdn.kupu.core.reporting.util.ReportSize.A3;
import static id.my.mdn.kupu.core.reporting.util.ReportSize.A4;
import static id.my.mdn.kupu.core.reporting.util.ReportSize.A5;
import static id.my.mdn.kupu.core.reporting.util.ReportSize.A6;
import jakarta.enterprise.context.Dependent;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;

@Dependent
public class ReportExporter {

    public void export(OutputStream outputStream, JasperPrint jasperPrint) {
        try {
            JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
        } catch (JRException ex) {
            Logger.getLogger(ReportExporter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void exportToFile(String filename, JasperPrint jasperPrint) {
        try {
            export(new FileOutputStream(filename), jasperPrint);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ReportExporter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void exportAlternate(OutputStream outputStream, JasperPrint jasperPrint1, JasperPrint jasperPrint2) {
        ByteArrayOutputStream baos1 = new ByteArrayOutputStream();
        ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
        export(baos1, jasperPrint1);
        export(baos2, jasperPrint2);
        Document document = new Document();
        try {
            PdfReader reader1 = new PdfReader(baos1.toByteArray());
            PdfReader reader2 = new PdfReader(baos2.toByteArray());
            int n = reader1.getNumberOfPages();

            PdfCopy copy = new PdfCopy(document, outputStream);

            document.open();

            for (int i = 1; i <= n; i++) {
                PdfImportedPage page1 = copy.getImportedPage(reader1, i);
                copy.addPage(page1);
                PdfImportedPage page2 = copy.getImportedPage(reader2, i);
                copy.addPage(page2);
            }

            document.close();

        } catch (DocumentException | IOException ex) {
            Logger.getLogger(ReportExporter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void exportCombine(OutputStream output, JasperPrint... jasperPrints) {
        try {
            Document document = new Document();
            PdfCopy copy = new PdfCopy(document, output);
            document.open();
            for (JasperPrint filledReport : jasperPrints) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                export(baos, filledReport);
                PdfReader reader = new PdfReader(baos.toByteArray());
                int pages = reader.getNumberOfPages();
                for (int i = 1; i <= pages; i++) {
                    PdfImportedPage page = copy.getImportedPage(reader, i);
                    copy.addPage(page);
                }
            }

            document.close();
        } catch (DocumentException | IOException ex) {
            Logger.getLogger(ReportExporter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void exportTiled(OutputStream output, int columns, int rows, ReportSize reportSize, JasperPrint... filledReports) {
        Rectangle container = getRectangle(reportSize);
        try {
            float containerWidth = container.getWidth();
            float containerHeight = container.getHeight();

            float stepX = containerWidth / columns;
            float stepY = containerHeight / rows;

            float maxX = containerWidth - stepX;
            float maxY = containerHeight - stepY;

            float dX = 0;
            float dY = maxY;

            Document document = new Document(container);
            PdfWriter writer = PdfWriter.getInstance(document, output);
            document.open();
            PdfContentByte cb = writer.getDirectContent();

            int contentCount = filledReports.length;
            int i = 0;
            int countX = columns;
            int countY = rows;
            while (i < contentCount) {
                while (i < contentCount) {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    export(baos, filledReports[i]);
                    PdfReader reader = new PdfReader(baos.toByteArray());
                    PdfImportedPage page;
                    page = writer.getImportedPage(reader, 1);

                    float scaleWidth = stepX / page.getWidth();
                    float scaleHeight = stepY / page.getWidth();
                    float scale = (scaleWidth <= scaleHeight ? scaleWidth : scaleHeight);

                    cb.addTemplate(page, scale, 0, 0, scale, dX, dY);

                    i++;

                    cb.setLineDash(2, 2.5f, 0f);
                    if (dX > 0) {
                        cb.moveTo(dX, dY);
                        cb.lineTo(dX, dY + stepY);
                    }
                    if (dY > 0 && countY != 1) {
                        cb.moveTo(dX, dY);
                        cb.lineTo(dX + stepX, dY);
                    }
                    if (i == contentCount && countX > 1) {
                        cb.moveTo(dX + stepX, dY);
                        cb.lineTo(dX + stepX, dY + stepY);
                    }
                    cb.closePathStroke();

                    countX--;
                    if (countX == 0) {
                        countX = columns;
                        dX = 0;
                        break;
                    }
                    dX += stepX;
                }
                dY = Math.abs(dY - stepY);
                countY--;
                if (countY == 0 && countX == columns) {
                    document.newPage();
                    countY = rows;
                    dY = maxY;
                }

            }

            document.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ReportExporter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException | IOException ex) {
            Logger.getLogger(ReportExporter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private Rectangle getRectangle(ReportSize reportSize) {
        switch(reportSize) {
            case A0:
                return PageSize.A0;
            case A1:
                return PageSize.A1;
            case A2:
                return PageSize.A2;
            case A3:
                return PageSize.A3;
            case A4:
                return PageSize.A4;
            case A5:
                return PageSize.A5;
            case A6:
                return PageSize.A6;
            default:
                return PageSize.A4;
        }
    }

}
