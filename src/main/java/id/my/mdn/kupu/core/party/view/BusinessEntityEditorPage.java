/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.core.party.view;

import id.my.mdn.kupu.core.party.dao.BusinessEntityFacade;
import id.my.mdn.kupu.core.party.entity.BusinessEntity;
import id.my.mdn.kupu.core.base.util.Result;
import id.my.mdn.kupu.core.base.view.FormPage;
import id.my.mdn.kupu.core.base.view.annotation.Form;
import id.my.mdn.kupu.core.party.entity.Organization;
import id.my.mdn.kupu.core.party.view.form.OrganizationEditorForm;
import jakarta.enterprise.context.ConversationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Named(value = "businessEntityEditorPage")
@ConversationScoped
public class BusinessEntityEditorPage extends FormPage<BusinessEntity> {
    
    @Inject
    private BusinessEntityFacade dao;
    
    @Inject @Form
    private OrganizationEditorForm form;
    
    private BusinessEntity parent;

    @Override
    public void load() {
        super.load();
        form.init(getEntity().getOrganization());
    }

    @Override
    protected BusinessEntity newEntity() {
        Organization org = Organization.builder().get();
        
        return BusinessEntity.builder()
                .organization(org)
                .parent(parent)
                .get();
    }

    @Override
    protected Result<String> save(BusinessEntity entity) {
        return dao.create(entity);
    }

    @Override
    protected Result<String> edit(BusinessEntity entity) {
        return dao.edit(entity);
    }

    public OrganizationEditorForm getForm() {
        return form;
    }

    public BusinessEntity getParent() {
        return parent;
    }

    public void setParent(BusinessEntity parent) {
        this.parent = parent;
    }
    
}
