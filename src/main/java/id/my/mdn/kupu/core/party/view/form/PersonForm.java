/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.party.view.form;

import id.my.mdn.kupu.core.base.util.Result;
import id.my.mdn.kupu.core.base.view.FormComponent;
import id.my.mdn.kupu.core.party.entity.Person;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Named;

/**
 *
 * @author aphasan
 */
@Named(value = "personForm")
@Dependent
public class PersonForm extends FormComponent<Person> {

    @Override
    protected void doPack() {

    }

    @Override
    protected Result<String> checkFormComponentValidity() {
        Result<String> result = new Result<>(true, null);
        result.success = entity.getName() != null && !entity.getName().isBlank();
        if (!result.success) {
            result.payload = "Nama kosong";
        }
        return result;
    }

}
