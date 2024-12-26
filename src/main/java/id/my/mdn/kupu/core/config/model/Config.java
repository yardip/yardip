/*
 * Copyright 2015 Arief Prihasanto <ariefp5758 at gmail.com>.
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
package id.my.mdn.kupu.core.config.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.TableGenerator;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Arief Prihasanto <ariefp5758 at gmail.com>
 */
@Entity
@Table(name = "CONFIG_CONFIG", uniqueConstraints = @UniqueConstraint(columnNames = {"MODULE_NAME", "CONFIG_KEY"}))
public class Config implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @TableGenerator(name = "Config_Config", table = "KEYGEN", allocationSize = 1)
    @GeneratedValue(generator = "Config_Config", strategy = GenerationType.TABLE)
    private Long id;
    
    @Column(name = "MODULE_NAME", length = 255, nullable = false)
    @NotNull
    private String module;
    
    @Column(name = "CONFIG_KEY", length = 255, nullable = false)
    @NotNull
    private String key;

    @Column(name = "CONFIG_VALUE", nullable = false)
    @NotNull
    private String value;

    private String remarks;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + Objects.hashCode(this.id);
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
        final Config other = (Config) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

}
