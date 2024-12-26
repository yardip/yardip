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
import id.my.mdn.kupu.core.party.dao.OrganizationFacade;
import id.my.mdn.kupu.core.party.entity.ContactType;
import id.my.mdn.kupu.core.party.entity.Organization;
import id.my.mdn.kupu.core.party.view.form.OrganizationForm;
import id.my.mdn.kupu.core.party.view.widget.PartyClassificationList;
import id.my.mdn.kupu.core.party.view.widget.PartyContactMechanismList;
import id.my.mdn.kupu.core.party.view.widget.PartyRoleList;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;

/**
 *
 * @author aphasan
 */
@Named(value = "organizationDetailPage")
@Dependent
public class OrganizationDetailPage extends FormPage implements Serializable {

    @Bookmarked
    private Organization party;

    @Inject
    @Form
    private OrganizationForm form;

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
    private OrganizationFacade organizationFacade;

    private UpdateListener updateListener = (o) -> {
    };

    @PostConstruct
    @Override
    public void init() {
        super.init();
    }

    @Override
    public void load() {
        super.load();
        form.setEntity(party);
        classificationListInit();
        roleListInit();
        electronicAddressListInit();
        postalAddressListInit();
        telecommunicationNumberListInit();
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

//    @Creator(of = "classificationList")
//    public void openTypeChooser() {
//        gotoChild(OrganizationTypeChooserPage.class)
//                .addParam("what")
//                .withValues(ADD_CLASSIFICATION)
//                .open();
//    }
//    @Deleter(of = "classificationList")
//    public void removeClassification(PartyClassification classification) {
//        classificationList.getSelector().setSelection(classification);
//        Confirmation.from(this).on(DELETE_CLASSIFICATION)
//                .withMessage("application.msg.deleteClassificationConfirm")
//                .andMessageParams(classificationList.getSelector().getSelection().getPartyType().getName())
//                .open();
//    }
//    @Creator(of = "roleList")
//    public void openRoleTypeChooser() {
//        gotoChild(PartyRoleTypeChooserPage.class)
//                .addParam("what")
//                .withValues(ADD_ROLE)
//                .addParam("partyType")
//                .withValues(party.getClass().getSimpleName())
//                .addParam("party")
//                .withValues(party)
//                .open();
//    }
//    @Deleter(of = "roleList")
//    public void removeRole(PartyRole role) {
//        roleList.getSelector().setSelection(role);
//        Confirmation.from(this).on(DELETE_ROLE)
//                .withMessage("application.msg.deleteConfirm")
//                .andMessageParams(roleList.getSelector().getSelection().getPartyRoleType().getName())
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
        organizationFacade.edit(party);
        updateListener.onUpdate(party);
    }

    public OrganizationForm getForm() {
        return form;
    }

    public void setForm(OrganizationForm form) {
        this.form = form;
    }

    public PartyClassificationList getClassificationList() {
        return classificationList;
    }

    public PartyRoleList getRoleList() {
        return roleList;
    }

    public Organization getParty() {
        return party;
    }

    public void setParty(Organization party) {
        this.party = party;
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

    public void setUpdateListener(UpdateListener updateListener) {
        this.updateListener = updateListener;
    }

    @Override
    protected Object newEntity() {
        return Organization.builder().get();
    }

    @Override
    protected Result<String> save(Object entity) {
        return new Result<>(true, "Fungsi tidak didefinisikan");
    }

    @Override
    protected Result<String> edit(Object entity) {
        return new Result<>(true, "Fungsi tidak didefinisikan");
    }

}
