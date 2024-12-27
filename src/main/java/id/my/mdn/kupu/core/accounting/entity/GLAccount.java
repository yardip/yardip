/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.core.accounting.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.TableGenerator;
import java.io.Serializable;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Entity
@Table(name = "ACCOUNTING__GLACCOUNT")
public class GLAccount implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @TableGenerator(name = "Accounting_GLAccount", table = "KEYGEN", allocationSize = 1)
    @GeneratedValue(generator = "Accounting_GLAccount", strategy = GenerationType.TABLE)
    private Long id;
    
    private String name;
    
    @Enumerated
    private GLAccountType accountType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GLAccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(GLAccountType accountType) {
        this.accountType = accountType;
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
        if (!(object instanceof GLAccount)) {
            return false;
        }
        GLAccount other = (GLAccount) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }
    
}
