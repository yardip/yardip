/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.party.view;

import id.my.mdn.kupu.core.base.util.Result;
import id.my.mdn.kupu.core.base.view.FormPage;
import id.my.mdn.kupu.core.base.view.annotation.Bookmarked;
import id.my.mdn.kupu.core.base.view.annotation.Creator;
import id.my.mdn.kupu.core.base.view.annotation.Deleter;
import id.my.mdn.kupu.core.base.view.annotation.Editor;
import id.my.mdn.kupu.core.base.view.annotation.Form;
import id.my.mdn.kupu.core.party.dao.PersonFacade;
import id.my.mdn.kupu.core.party.entity.ContactType;
import id.my.mdn.kupu.core.party.entity.Person;
import id.my.mdn.kupu.core.party.view.form.PersonForm;
import id.my.mdn.kupu.core.party.view.widget.PartyClassificationList;
import id.my.mdn.kupu.core.party.view.widget.PartyContactMechanismList;
import id.my.mdn.kupu.core.party.view.widget.PartyRoleList;
import id.my.mdn.kupu.core.party.view.widget.PersonIdentityList;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.logging.Logger;

/**
 *
 * @author aphasan
 */
@Named(value = "personDetailPage")
@Dependent
public class PersonDetailPage extends FormPage<Person> implements Serializable {

    private static final Logger Log = Logger.getLogger(PersonDetailPage.class.getCanonicalName());

    @Bookmarked
    private Person party;

    @Inject
    @Form
    private PersonForm form;

    @Bookmarked
    @Inject
    private PersonIdentityList identityList;

    @Bookmarked
    @Inject
    private PartyClassificationList classificationList;

    @Bookmarked
    @Inject
    private PartyRoleList roleList;

    @Bookmarked
    @Inject
    private PartyContactMechanismList postalAddressList;

    @Bookmarked
    @Inject
    private PartyContactMechanismList telecommunicationNumberList;

    @Bookmarked
    @Inject
    private PartyContactMechanismList electronicAddressList;

    @Inject
    private PersonFacade partyFacade;

    private UpdateListener updateListener = (o) -> {
    };

    public void setUpdateListener(UpdateListener updateListener) {
        this.updateListener = updateListener;
    }

    @PostConstruct
    @Override
    public void init() {
        super.init();
    }

    @Override
    public void load() {
        super.load();
        form.setEntity(party);
        identityListInit();
        classificationListInit();
        roleListInit();
        electronicAddressListInit();
        postalAddressListInit();
        telecommunicationNumberListInit();
    }

    private void identityListInit() {
        identityList.setName("identityTbl");
        identityList.setPerson((Person) party);
        identityList.getSelector().setSelectionsLabel("is");
        identityList.getPager().setPageSizeLabel("ip");
        identityList.getPager().setOffsetLabel("io");
    }

    private void classificationListInit() {
        classificationList.setName("classificationTbl");
        classificationList.setParty(party);
        classificationList.getSelector().setSelectionsLabel("cs");
        classificationList.getPager().setPageSizeLabel("cp");
        classificationList.getPager().setOffsetLabel("co");
    }

    private void roleListInit() {
        roleList.setName("roleTbl");
        roleList.setParty(party);
        roleList.getSelector().setSelectionsLabel("rs");
        roleList.getPager().setPageSizeLabel("rp");
        roleList.getPager().setOffsetLabel("ro");
    }

    private void electronicAddressListInit() {
        electronicAddressList.setName("electronicAddressTbl");
        electronicAddressList.setParty(party);
        electronicAddressList.setContactType(ContactType.ElectronicAddress);
        electronicAddressList.getSelector().setSelectionsLabel("es");
        electronicAddressList.getPager().setPageSizeLabel("ep");
        electronicAddressList.getPager().setOffsetLabel("eo");
    }

    private void postalAddressListInit() {
        postalAddressList.setName("postalAddressTbl");
        postalAddressList.setParty(party);
        postalAddressList.setContactType(ContactType.PostalAddress);
        postalAddressList.getSelector().setSelectionsLabel("ps");
        postalAddressList.getPager().setPageSizeLabel("pp");
        postalAddressList.getPager().setOffsetLabel("po");
    }

    private void telecommunicationNumberListInit() {
        telecommunicationNumberList.setName("telecommunicationNumberTbl");
        telecommunicationNumberList.setParty(party);
        telecommunicationNumberList.setContactType(ContactType.TelecommunicationNumber);
        telecommunicationNumberList.getSelector().setSelectionsLabel("ts");
        telecommunicationNumberList.getPager().setPageSizeLabel("tp");
        telecommunicationNumberList.getPager().setOffsetLabel("to");
    }

    @Creator(of = "identityList")
    private void openIdentityCreator() {
        gotoChild(PersonIdentityEditorPage.class)
                .addParam("person")
                .withValues(party)
                .open();
    }

    @Editor(of = "identityList")
    private void openIdentityEditor() {
        gotoChild(PersonIdentityEditorPage.class)
                .addParam("person")
                .withValues(party)
                .addParam("entity")
                .withValues(identityList.getSelection())
                .open();
    }

    @Deleter(of = "identityList")
    private void removeIdentity() {
        identityList.delete(identityList.getSelector().getSelections());
    }

//    @Creator(of = "classificationList")
//    private void openPartyTypeChooser() {
//        if (party instanceof Person) {
//            gotoChild(PersonTypeChooserPage.class)
//                    .addParam("what")
//                    .withValues(ADD_CLASSIFICATION)
//                    .open();
//        } else {
//            gotoChild(OrganizationTypeChooserPage.class)
//                    .addParam("what")
//                    .withValues(ADD_CLASSIFICATION)
//                    .open();
//        }
//    }

//    @Deleter(of = "classificationList")
//    public void removeClassification(PartyClassification classification) {
//        classificationList.getSelector().setSelection(classification);
//        Confirmation.from(this).on(DELETE_CLASSIFICATION)
//                .withMessage("application.msg.deleteClassificationConfirm")
//                .andMessageParams(classificationList.getSelector().getSelection().getPartyType().getName())
//                .open();
//    }

    @Creator(of = "electronicAddressList")
    public void openElectronicAddressCreator() {
        gotoChild(ElectronicAddressEditorPage.class)
                .addParam("party")
                .withValues(party)
                .open();
    }

    @Editor(of = "electronicAddressList")
    public void openElectronicAddressEditor() {
        gotoChild(ElectronicAddressEditorPage.class)
                .addParam("party")
                .withValues(party)
                .addParam("entity")
                .withValues(electronicAddressList.getSelection())
                .open();
    }

    @Deleter(of = "electronicAddressList")
    public void removeElectronicAddress() {
        electronicAddressList.delete(electronicAddressList.getSelector().getSelections());
    }

    @Creator(of = "postalAddressList")
    public void openPostalAddressCreator() {
        gotoChild(PostalAddressEditorPage.class)
                .addParam("party")
                .withValues(party)
                .open();
    }

    @Editor(of = "postalAddressList")
    public void openPostalAddressEditor() {
        gotoChild(PostalAddressEditorPage.class)
                .addParam("party")
                .withValues(party)
                .addParam("entity")
                .withValues(postalAddressList.getSelection())
                .open();
    }

    @Deleter(of = "postalAddressList")
    public void removePostalAddress() {
        postalAddressList.delete(postalAddressList.getSelector().getSelections());
    }

    @Creator(of = "telecommunicationNumberList")
    public void openTelecommunicationNumberCreator() {
        gotoChild(TelecommunicationNumberEditorPage.class)
                .addParam("party")
                .withValues(party)
                .open();
    }

    @Editor(of = "telecommunicationNumberList")
    public void openTelecommunicationNumberEditor() {
        gotoChild(TelecommunicationNumberEditorPage.class)
                .addParam("party")
                .withValues(party)
                .addParam("entity")
                .withValues(telecommunicationNumberList.getSelection())
                .open();
    }

    @Deleter(of = "telecommunicationNumberList")
    public void removeTelecommunicationNumber() {
        telecommunicationNumberList.delete(telecommunicationNumberList.getSelector().getSelections());
    }

    public void updateParty() {
        partyFacade.edit(party);
        updateListener.onUpdate(party);
    }

    public PersonForm getForm() {
        return form;
    }

    public PartyClassificationList getClassificationList() {
        return classificationList;
    }

    public PartyRoleList getRoleList() {
        return roleList;
    }

    public Person getParty() {
        return party;
    }

    public void setParty(Person party) {
        this.party = party;
    }

    public PersonIdentityList getIdentityList() {
        return identityList;
    }

    public PartyContactMechanismList getElectronicAddressList() {
        return electronicAddressList;
    }

    public PartyContactMechanismList getPostalAddressList() {
        return postalAddressList;
    }

    public PartyContactMechanismList getTelecommunicationNumberList() {
        return telecommunicationNumberList;
    }

    @Override
    protected Person newEntity() {
        return Person.builder().get();
    }

    @Override
    protected  Result<String> save(Person entity) {
        return new Result<>(true, "Fungsi tidak didefinisikan");
    }

    @Override
    protected  Result<String> edit(Person entity) {
        return new Result<>(true, "Fungsi tidak didefinisikan");
    }

}
