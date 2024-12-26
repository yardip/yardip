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

import id.my.mdn.kupu.core.reporting.model.ReportTemplate;
import jakarta.ejb.Stateless;
import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

@Stateless
public class ReportLoader {

    public JasperDesign load(ReportTemplate reportTemplate) throws ReportLoadingException {
        try {
            String sourceContent = reportTemplate.getSourceTemplate();
            JasperDesign design = JRXmlLoader.load(new ByteArrayInputStream(sourceContent.getBytes("UTF-8")));
            return design;
        } catch (UnsupportedEncodingException ex) {
            throw new ReportLoadingException("Bad template", ex);
        } catch (JRException ex) {
            throw new ReportLoadingException(ex);
        }
    }

}
