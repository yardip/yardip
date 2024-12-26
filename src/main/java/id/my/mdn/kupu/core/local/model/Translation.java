/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.local.model;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.persistence.TableGenerator;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Locale;

/**
 *
 * @author aphasan
 */
@Entity
@Table(name = "TRANSLATION_TRANSLATION", uniqueConstraints = @UniqueConstraint(columnNames = {"LOCALE", "TRANSLATION_KEY"}))
public class Translation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @TableGenerator(name = "Translation_Translation", table = "KEYGEN", allocationSize = 1)
    @GeneratedValue(generator = "Translation", strategy = GenerationType.TABLE)
    private Long id;
    
    @Column(name = "LOCALE", length = 5, nullable = false)
    @NotNull
    @Convert(converter=LocaleConverter.class)
    private Locale locale;
    
    @Column(name = "TRANSLATION_KEY", length = 255, nullable = false)
    @NotNull
    private String key;
    
    @Lob
    @Column(name = "TRANSLATION_VALUE", nullable = false)
    @NotNull
    private String value;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Translation)) {
            return false;
        }
        Translation other = (Translation) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return id != null ? String.valueOf(id) : null;
    }

}
