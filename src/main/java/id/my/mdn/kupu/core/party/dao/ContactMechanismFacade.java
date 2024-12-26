/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.party.dao;

import id.my.mdn.kupu.core.base.dao.AbstractFacade;
import id.my.mdn.kupu.core.base.util.Result;
import id.my.mdn.kupu.core.party.entity.ContactMechanism;
import id.my.mdn.kupu.core.party.entity.ElectronicAddress;
import id.my.mdn.kupu.core.party.entity.PostalAddress;
import id.my.mdn.kupu.core.party.entity.TelecommunicationNumber;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

/**
 *
 * @author aphasan
 */
@Stateless
public class ContactMechanismFacade extends AbstractFacade<ContactMechanism> {
    
    @Inject
    private PostalAddressFacade postalAddressFacade;
    
    @Inject
    private TelecommunicationNumberFacade telecommunicationNumberFacade;
    
    @Inject
    private ElectronicAddressFacade electronicAddressFacade;

    @Inject
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ContactMechanismFacade() {
        super(ContactMechanism.class);
    }

    @Override
    public Result<String> create(ContactMechanism entity) {
        if(entity instanceof PostalAddress) {
            return postalAddressFacade.create((PostalAddress) entity);
        } else if (entity instanceof  TelecommunicationNumber) {
            return telecommunicationNumberFacade.create((TelecommunicationNumber) entity);
        } else {
            return electronicAddressFacade.create((ElectronicAddress) entity);
        }
    }

}
