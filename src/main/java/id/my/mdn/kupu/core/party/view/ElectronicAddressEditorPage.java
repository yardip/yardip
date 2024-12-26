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
import id.my.mdn.kupu.core.party.dao.PartyContactMechanismFacade;
import id.my.mdn.kupu.core.party.entity.ElectronicAddress;
import id.my.mdn.kupu.core.party.entity.Party;
import id.my.mdn.kupu.core.party.entity.PartyContactMechanism;
import id.my.mdn.kupu.core.party.view.form.ElectronicAddressForm;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 *
 * @author aphasan
 */
@Named(value = "electronicAddressEditorPage")
@ViewScoped
public class ElectronicAddressEditorPage extends FormPage<PartyContactMechanism> {

    @Inject
    private PartyContactMechanismFacade dao;

    @Inject
    @Form
    private ElectronicAddressForm form;

    @Bookmarked
    private Party party;

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
    protected PartyContactMechanism newEntity() {
        PartyContactMechanism contact = PartyContactMechanism.builder()
                .party(party)
                .contactMechanism(new ElectronicAddress())
                .get();

        return contact;
    }

    @Override
    protected Result<String> save(PartyContactMechanism entity) {
        if (form.getValidityChecker().isValid().success) {
            Result<String> create = dao.create(entity);
            return create;
        } else {
            setCreateNew(false);
            return new Result<>(true, "Data sudah ada");
        }
    }

    @Override
    protected Result<String> edit(PartyContactMechanism entity) {
        return dao.edit(entity);
    }
    
    public Party getParty() {
        return party;
    }

    public void setParty(Party party) {
        this.party = party;
    }

    public ElectronicAddressForm getElectronicAddressForm() {
        return form;
    }

}
