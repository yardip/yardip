/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.party.view;

import id.my.mdn.kupu.core.base.util.Result;
import id.my.mdn.kupu.core.base.view.FormPage;
import id.my.mdn.kupu.core.base.view.annotation.Bookmarked;
import id.my.mdn.kupu.core.base.view.annotation.Form;
import id.my.mdn.kupu.core.party.dao.PersonIdentityFacade;
import id.my.mdn.kupu.core.party.entity.Person;
import id.my.mdn.kupu.core.party.entity.PersonIdentity;
import id.my.mdn.kupu.core.party.view.form.PersonIdentityForm;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 *
 * @author aphasan
 */
@Named(value = "personIdentityEditorPage")
@ViewScoped
public class PersonIdentityEditorPage extends FormPage<PersonIdentity> {
    
    @Inject
    private PersonIdentityFacade dao;
    
    @Inject @Form
    private PersonIdentityForm form;
    
    @Bookmarked
    private Person person;

    @PostConstruct
    @Override
    protected void init() {
        super.init();
    }
    
    @Override
    public void load() {
        super.load();
        form.init(entity);
    }

    @Override
    protected PersonIdentity newEntity() {
        PersonIdentity identity = new PersonIdentity();
        identity.setPerson(person);
        return identity;
    }

    @Override
    protected  Result<String> save(PersonIdentity entity) {
        return dao.create(entity);
    }

    @Override
    protected  Result<String> edit(PersonIdentity entity) {
        return dao.edit(entity);
    }

    public PersonIdentityForm getForm() {
        return form;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
    
}
