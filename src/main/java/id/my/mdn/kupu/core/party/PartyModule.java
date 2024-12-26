/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.core.party;

import id.my.mdn.kupu.core.base.AbstractModule;
import id.my.mdn.kupu.core.party.dao.PartyRelationshipTypeFacade;
import id.my.mdn.kupu.core.party.dao.PartyRoleTypeFacade;
import id.my.mdn.kupu.core.party.dao.PostalAddressPurposeTypeFacade;
import id.my.mdn.kupu.core.party.entity.PostalAddressPurposeType;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;

/**
 *
 * @author aphasan
 */
@Dependent
public class PartyModule extends AbstractModule {

    @Inject
    private PartyRoleTypeFacade partyRoleTypeFacade;

    @Inject
    private PartyRelationshipTypeFacade partyRelTypeFacade;
    
    @Inject
    private PostalAddressPurposeTypeFacade postalAddressPurposeTypeFacade;
    
    @Override
    protected void init() {
        loadRoleTypes();
        loadContactMechanismPurposeType();
    }

    private void loadRoleTypes() {
        
    /************************** Party Role Types ****************************/
        partyRoleTypeFacade.createTypeIfNotExist("ParentOrganization", "Parent Organization");
        partyRoleTypeFacade.createTypeIfNotExist("SubsidiaryOrganization", "Subsidiary Organization");
        partyRoleTypeFacade.createTypeIfNotExist("ApplicationUser", "Application User");
        partyRoleTypeFacade.createTypeIfNotExist("ApplicationSecurityGroup", "Application Security Group");
        partyRoleTypeFacade.createTypeIfNotExist("BusinessEntity", "Business Entity");               
        partyRoleTypeFacade.createTypeIfNotExist("Employee", "Employee");
        
    /************************** Party Relationship Types ****************************/    
        partyRelTypeFacade.createTypeIfNotExist("Employment", "Employment");
    }    
    
    private void loadContactMechanismPurposeType() {
        PostalAddressPurposeType postalAddressPurposeType = new PostalAddressPurposeType();
        
        postalAddressPurposeType.setRemarks("Alamat Rumah");
        postalAddressPurposeTypeFacade.createIfNotExist(postalAddressPurposeType, "remarks");
        
        postalAddressPurposeType.setRemarks("Alamat Kantor");
        postalAddressPurposeTypeFacade.createIfNotExist(postalAddressPurposeType, "remarks");
        
        postalAddressPurposeType.setRemarks("Alamat Lainnya");
        postalAddressPurposeTypeFacade.createIfNotExist(postalAddressPurposeType, "remarks");
    }

    @Override
    protected String getLabel() {
        return "party.module.title";
    }

    @Override
    protected int getOrder() {
        return Integer.MAX_VALUE - 5;
    }

    @Override
    protected boolean visible() {
        return false;
    }
    
}
