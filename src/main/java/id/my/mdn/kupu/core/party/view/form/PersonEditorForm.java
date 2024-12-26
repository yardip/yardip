/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.core.party.view.form;

import id.my.mdn.kupu.core.base.util.Result;
import id.my.mdn.kupu.core.base.view.FormComponent;
import id.my.mdn.kupu.core.base.view.annotation.Form;
import id.my.mdn.kupu.core.party.entity.ElectronicAddress;
import id.my.mdn.kupu.core.party.entity.PartyContactMechanism;
import id.my.mdn.kupu.core.party.entity.Person;
import id.my.mdn.kupu.core.party.entity.PersonIdentity;
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
@Named(value = "personEditorForm")
@Dependent
public class PersonEditorForm extends FormComponent<Person> {

    @Inject
    @Form
    private PersonForm personForm;

    @Inject
    @Form
    private PersonIdentityForm personIdentityForm;

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
    public void init(Person person) {
        super.init(person);
        
        personForm.setEntity(person);

        personIdentityForm.setEntity(person.getIdentity() != null ? person.getIdentity() : new PersonIdentity());

        postalAddressForm.setEntity(
                person.getPostalAddress() != null ? person.getPostalAddress()
                : PartyContactMechanism.builder()
                        .contactMechanism(new PostalAddress())
                        .defaulted(true).get());

        telecommunicationNumberForm.setEntity(person.getTelecommunicationNumber() != null
                ? person.getTelecommunicationNumber()
                : PartyContactMechanism.builder()
                        .contactMechanism(new TelecommunicationNumber())
                        .defaulted(true).get());

        electronicAddressForm.setEntity(person.getElectronicAddress() != null
                ? person.getElectronicAddress()
                : PartyContactMechanism.builder()
                        .contactMechanism(new ElectronicAddress())
                        .defaulted(true).get());
    }

    @Override
    protected Result<String> checkFormComponentValidity() {
        Result<String> result = new Result<>(true, null);

        Result<String> personResult = personForm.checkFormComponentValidity();

        Result<String> identityResult = personIdentityForm.checkFormComponentValidity();
        if (identityResult.isNotSuccess()) {
            personIdentityForm.setEntity(null);
        }

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

        result.success = personResult.success;

        List<String> messages = new ArrayList<>();

        messages.add(personResult.payload);
        messages.add(identityResult.payload);
        messages.add(postalAddressResult.payload);
        messages.add(telcoNumberResult.payload);
        messages.add(electronicAddressResult.payload);

        result.payload = messages.stream().filter(msg -> msg != null)
                .collect(Collectors.joining(", "));

        return result;
    }

    @Override
    protected void doPack() {
        personForm.doPack();

        if (personIdentityForm.getEntity() != null) {
            personIdentityForm.getEntity().setPerson(entity);
            personIdentityForm.doPack();
            entity.getIdentities().add(personIdentityForm.getEntity());
        }

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

    public PersonForm getPersonForm() {
        return personForm;
    }

    public PersonIdentityForm getPersonIdentityForm() {
        return personIdentityForm;
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
