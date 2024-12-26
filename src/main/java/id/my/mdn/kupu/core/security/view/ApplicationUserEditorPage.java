/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.security.view;

import id.my.mdn.kupu.core.base.util.Result;
import id.my.mdn.kupu.core.base.view.FormPage;
import id.my.mdn.kupu.core.base.view.annotation.Bookmarked;
import id.my.mdn.kupu.core.base.view.annotation.Form;
import id.my.mdn.kupu.core.party.entity.Party;
import id.my.mdn.kupu.core.party.entity.Person;
import id.my.mdn.kupu.core.party.view.form.PersonEditorForm;
import id.my.mdn.kupu.core.security.dao.ApplicationUserFacade;
import id.my.mdn.kupu.core.security.model.ApplicationSecurityGroup;
import id.my.mdn.kupu.core.security.model.ApplicationUser;
import id.my.mdn.kupu.core.security.util.PasswordUtil;
import jakarta.enterprise.context.ConversationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.List;

/**
 *
 * @author aphasan
 */
@Named(value = "applicationUserEditorPage")
@ConversationScoped
public class ApplicationUserEditorPage extends FormPage<ApplicationUser> {

    @Inject
    private ApplicationUserFacade dao;

    @Inject
    @Form
    private PersonEditorForm form;

    private String confirmedPassword;

    @Bookmarked
    private Party party;

    @Bookmarked
    private String username;

    @Bookmarked
    private List<ApplicationSecurityGroup> groups;

    @Override
    public void load() {
        super.load();
        form.init(getEntity().getPerson());
        form.setValidityChecker(this::checkValid);
        form.setPacker(this::doPack);
    }

    private Result<String> checkValid() {
        Result<String> validity = form.getPersonForm().getValidityChecker().isValid();
        boolean nameExist = dao.isNameAlreadyExist(entity.getUsername());
        if (nameExist) {
            validity.success = false;
            validity.payload = "Nama user telah dipakai!";
        }
        return validity;
    }

    private void doPack() {
        form.getPersonForm().getPacker().pack();
    }

    @Override
    protected ApplicationUser newEntity() {
        Person person = ((party != null && (party instanceof Person)) ? (Person) party : Person.builder().get());
        String suggestion = (!person.getFirstName().isBlank()
                ? person.getFirstName().toLowerCase()
                : "appuser") + PasswordUtil.generateUserSuffix();
        ApplicationUser.Builder userBuilder = ApplicationUser.builder()
                .withParty(party)
                .withUsername(suggestion)
                .withPassword(PasswordUtil.generateRandomPassword());

        for (ApplicationSecurityGroup group : groups) {
            userBuilder.addToGroup(group);
        }

        return userBuilder.get();
    }

    public void suggestPassword() {
        String pass = PasswordUtil.generateRandomPassword();
        getEntity().setPassword(pass);
        confirmedPassword = pass;
    }

    @Override
    protected Result<String> save(ApplicationUser entity) {
        return dao.create(entity);
    }

    @Override
    protected Result<String> edit(ApplicationUser entity) {
        return dao.edit(entity);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getConfirmedPassword() {
        return confirmedPassword;
    }

    public void setConfirmedPassword(String confirmedPassword) {
        this.confirmedPassword = confirmedPassword;
    }

    public PersonEditorForm getForm() {
        return form;
    }

    public Party getParty() {
        return party;
    }

    public void setParty(Party party) {
        this.party = party;
    }

    public List<ApplicationSecurityGroup> getGroups() {
        return groups;
    }

    public void setGroups(List<ApplicationSecurityGroup> groups) {
        this.groups = groups;
    }

}
