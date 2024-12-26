/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.core.security.view.widget;

import id.my.mdn.kupu.core.base.util.Result;
import id.my.mdn.kupu.core.base.view.FormPage;
import id.my.mdn.kupu.core.base.view.annotation.Form;
import id.my.mdn.kupu.core.party.entity.Organization;
import id.my.mdn.kupu.core.party.view.form.OrganizationEditorForm;
import id.my.mdn.kupu.core.security.dao.ApplicationSecurityGroupFacade;
import id.my.mdn.kupu.core.security.model.ApplicationSecurityGroup;
import jakarta.enterprise.context.ConversationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Named(value = "applicationSecurityGroupEditorPage")
@ConversationScoped
public class ApplicationSecurityGroupEditorPage extends FormPage<ApplicationSecurityGroup> {

    @Inject
    private ApplicationSecurityGroupFacade dao;

    @Inject
    @Form
    private OrganizationEditorForm form;

    @Override
    public void load() {
        super.load();
        form.init(getEntity().getOrganization());
        form.setValidityChecker(this::checkValid);
        form.setPacker(this::doPack);
    }

    private Result<String> checkValid() {
        Result<String> validity = form.getOrganizationForm().getValidityChecker().isValid();
        boolean nameExist = dao.isNameAlreadyExist("security", entity.getOrganization().getName());
        if (nameExist) {
            validity.success = false;
            validity.payload = "Nama telah dipakai!";
        }
        return validity;
    }

    private void doPack() {
        form.getOrganizationForm().getPacker().pack();
    }

    @Override
    protected ApplicationSecurityGroup newEntity() {
        Organization org = Organization.builder().get();

        return ApplicationSecurityGroup.builder()
                .organization(org)
                .module("security")
                .get();
    }

    @Override
    protected Result<String> save(ApplicationSecurityGroup entity) {
        return dao.create(entity);
    }

    @Override
    protected Result<String> edit(ApplicationSecurityGroup entity) {
        return dao.edit(entity);
    }

    public OrganizationEditorForm getForm() {
        return form;
    }

}
