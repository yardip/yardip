/*
 * Copyright 2014 Arief Prihasanto <ariefp5758 at gmail.com>.
 * All rights reserved

 * A lot of time, effort and money is spent designing and implementing the software.
 * All system design, text, graphics, the selection and arrangement thereof, and
 * all software compilations, underlying source code, software and all other material
 * on this software are copyright Arief Prihasanto <ariefp5758 at gmail.com> and any affiliates.
 * 
 * In simple terms, every element of this software is protected by copyright.
 * Unless you have our express written permission, you are not allowed
 * to copy partially and or completely, modify partially and or completely,
 * use partially and or completely and or reproduce any part of this  software
 * in any way, shape and or form.
 * 
 * Taking material from other source code and or document Arief Prihasanto <ariefp5758 at gmail.com> and affiliates has designed is
 * also prohibited. You can be prosecuted by the licensee as well as by us as licensor.
 * 
 * Any other use of materials of this software, including reproduction for purposes other
 * than that noted in the business agreement, modification, distribution, or republication,
 * without the prior written permission of Arief Prihasanto <ariefp5758 at gmail.com> is strictly prohibited.
 * 
 * The source code, partially and or completely, shall not be presented and or shown
 * and or performed to public and or other parties without the prior written permission
 * of Arief Prihasanto <ariefp5758 at gmail.com>

 */
package id.my.mdn.kupu.core.reporting.jasperreports;

import id.my.mdn.kupu.core.reporting.exception.ReportCompilationException;
import jakarta.ejb.Stateless;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;

@Stateless
public class ReportCompiler {

    public JasperReport compile(JasperDesign jasperDesign) throws ReportCompilationException {
        try {
            JasperReport compiled = JasperCompileManager.compileReport(jasperDesign);
            return compiled;
        } catch (JRException ex) {
            Logger.getLogger(ReportCompiler.class.getName()).log(Level.SEVERE, null, ex);
            throw new ReportCompilationException("Report Compilation Failed !", ex);
        }
    }

    public JasperReport compile(InputStream is) throws ReportCompilationException {
        try {
            JasperReport compiled = JasperCompileManager
                    .compileReport(is);
            return compiled;
        } catch (JRException ex) {
            Logger.getLogger(ReportCompiler.class.getName()).log(Level.SEVERE, null, ex);
            throw new ReportCompilationException("Report Compilation Failed !", ex);
        }
    }

}
