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
package id.my.mdn.kupu.core.reporting.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.Objects;



/**
 *
 * @author Arief Prihasanto <ariefp5758 at gmail.com>
 */
@Entity
@Table(name = "REPORTING_REPORTTEMPLATE")
@EntityListeners(ReportTemplateListener.class)
public class ReportTemplate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Column(unique = true)
    private String name;
     
    private String uri;

    @Lob
    @Column(columnDefinition = "CLOB NOT NULL")
    private String sourceTemplate;

    @Lob     
    private byte[] compiledTemplate;
   
    private String remarks;

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ReportTemplate other = (ReportTemplate) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getSourceTemplate() {
        return sourceTemplate;
    }

    public void setSourceTemplate(String sourceTemplate) {
        this.sourceTemplate = sourceTemplate;
    }

    public byte[] getCompiledTemplate() {
        return compiledTemplate;
    }

    public void setCompiledTemplate(byte[] compiledTemplate) {
        this.compiledTemplate = compiledTemplate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

}
