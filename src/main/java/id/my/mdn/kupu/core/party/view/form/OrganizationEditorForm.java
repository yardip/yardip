/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.core.party.view.form;

import id.my.mdn.kupu.core.base.util.Result;
import id.my.mdn.kupu.core.base.view.FormComponent;
import id.my.mdn.kupu.core.base.view.annotation.Form;
import id.my.mdn.kupu.core.party.entity.ElectronicAddress;
import id.my.mdn.kupu.core.party.entity.Organization;
import id.my.mdn.kupu.core.party.entity.PartyContactMechanism;
import id.my.mdn.kupu.core.party.entity.PostalAddress;
import id.my.mdn.kupu.core.party.entity.TelecommunicationNumber;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Named(value = "organizationEditorForm")
@Dependent
public class OrganizationEditorForm extends FormComponent<Organization> {

    @Inject
    @Form
    private OrganizationForm organizationForm;

    @Inject
    @Form
    private PostalAddressForm postalAddressForm;

    @Inject
    @Form
    private TelecommunicationNumberForm telecommunicationNumberForm;

    @Inject
    @Form
    private ElectronicAddressForm electronicAddressForm;

    @Override
    public void init(Organization organization) {
        super.init(organization);
        
        organizationForm.setEntity(organization);
        
        postalAddressForm.setEntity(
                organization.getPostalAddress() != null ? organization.getPostalAddress()
                : PartyContactMechanism.builder()
                        .contactMechanism(new PostalAddress())
                        .defaulted(true).get());
        
        telecommunicationNumberForm.setEntity(organization.getTelecommunicationNumber() != null
                ? organization.getTelecommunicationNumber()
                : PartyContactMechanism.builder()
                        .contactMechanism(new TelecommunicationNumber())
                        .defaulted(true).get());
        
        electronicAddressForm.setEntity(organization.getElectronicAddress() != null
                ? organization.getElectronicAddress()
                : PartyContactMechanism.builder()
                        .contactMechanism(new ElectronicAddress())
                        .defaulted(true).get());
    }

    @Override
    protected Result<String> checkFormComponentValidity() {
        Result<String> result = new Result<>(true, null);

        Result<String> organizationResult = organizationForm.checkFormComponentValidity();

        Result<String> postalAddressResult = postalAddressForm.checkFormComponentValidity();
        if (postalAddressResult.isNotSuccess()) {
            postalAddressForm.setEntity(null);
        }

        Result<String> telcoNumberResult = telecommunicationNumberForm.checkFormComponentValidity();
        if (telcoNumberResult.isNotSuccess()) {
            telecommunicationNumberForm.setEntity(null);
        }

        Result<String> electronicAddressResult = electronicAddressForm.checkFormComponentValidity();
        if (electronicAddressResult.isNotSuccess()) {
            electronicAddressForm.setEntity(null);
        }

        result.success = organizationResult.success;

        List<String> messages = new ArrayList<>();

        messages.add(organizationResult.payload);
        messages.add(postalAddressResult.payload);
        messages.add(telcoNumberResult.payload);
        messages.add(electronicAddressResult.payload);

        result.payload = messages.stream().filter(msg -> msg != null)
                .collect(Collectors.joining(", "));

        return result;
    }

    @Override
    protected void doPack() {
        organizationForm.doPack();

        if (postalAddressForm.getEntity() != null) {
            postalAddressForm.getEntity().setParty(entity);
            postalAddressForm.doPack();
            entity.getContactMechanisms().add(postalAddressForm.getEntity());
        }

        if (telecommunicationNumberForm.getEntity() != null) {
            telecommunicationNumberForm.getEntity().setParty(entity);
            telecommunicationNumberForm.doPack();
            entity.getContactMechanisms().add(telecommunicationNumberForm.getEntity());
        }

        if (electronicAddressForm.getEntity() != null) {
            electronicAddressForm.getEntity().setParty(entity);
            electronicAddressForm.doPack();
            entity.getContactMechanisms().add(electronicAddressForm.getEntity());
        }
    }

    public OrganizationForm getOrganizationForm() {
        return organizationForm;
    }

    public PostalAddressForm getPostalAddressForm() {
        return postalAddressForm;
    }

    public TelecommunicationNumberForm getTelecommunicationNumberForm() {
        return telecommunicationNumberForm;
    }

    public ElectronicAddressForm getElectronicAddressForm() {
        return electronicAddressForm;
    }

}
