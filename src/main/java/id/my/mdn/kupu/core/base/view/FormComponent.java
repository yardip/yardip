/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.base.view;

import id.my.mdn.kupu.core.base.util.Result;
import java.io.Serializable;

/**
 *
 * @author aphasan
 * @param <T>
 */
public abstract class FormComponent<T> implements Serializable {
    
    @FunctionalInterface
    public static interface ValidityChecker {
        Result<String> isValid();
    }
    
    @FunctionalInterface
    public static interface Packer {
        void pack();
    }
    
    protected T entity;
    protected ValidityChecker validityChecker = () -> checkFormComponentValidity();
    protected Packer packer = () -> doPack();
    
    private boolean required = false;
    protected abstract Result<String> checkFormComponentValidity();
    protected abstract void doPack();
    
    public void init(T entity) {
        this.entity = entity;
    };

    public T getEntity() {
        return entity;
    }

    public void setEntity(T entity) {
        this.entity = entity;
    }
    
    public boolean isRequired() {
        return this.required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public ValidityChecker getValidityChecker() {
        return validityChecker;
    }

    public void setValidityChecker(ValidityChecker validityChecker) {
        this.validityChecker = validityChecker;
    }

    public Packer getPacker() {
        return packer;
    }

    public void setPacker(Packer packer) {
        this.packer = packer;
    }
}
