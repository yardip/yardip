/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.party.view.form;

import id.my.mdn.kupu.core.base.util.Result;
import id.my.mdn.kupu.core.base.view.FormComponent;
import id.my.mdn.kupu.core.party.entity.PersonIdentity;
import id.my.mdn.kupu.core.party.view.widget.PersonIdentityTypeList;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 *
 * @author aphasan
 */
@Named(value = "personIdentityForm")
@Dependent
public class PersonIdentityForm extends FormComponent<PersonIdentity> {

    @Inject
    private PersonIdentityTypeList identityTypeList;

    @Override
    public Result<String> checkFormComponentValidity() {
        Result<String> result = new Result<>(true, null);
        result.success = (entity.getIdType() != null) 
                && (entity.getCode() != null)
                && !entity.getCode().isEmpty();
        
        if(!result.success) result.payload = "Tanda pengenal tidak lengkap ";
        return result;
    }

    @Override
    protected void doPack() {        
//        entity.getPerson().getIdentities().add(entity);
    }

    public PersonIdentityTypeList getIdentityTypeList() {
        return identityTypeList;
    }
    
    
}
