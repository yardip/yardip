/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.party.dao;

import id.my.mdn.kupu.core.base.dao.AbstractFacade;
import id.my.mdn.kupu.core.party.entity.TelecommunicationNumberPurposeType;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

/**
 *
 * @author aphasan
 */
@Stateless
public class TelecommunicationNumberPurposeTypeFacade extends AbstractFacade<TelecommunicationNumberPurposeType> {

    @Inject
    private EntityManager em;

    public TelecommunicationNumberPurposeTypeFacade() {
        super(TelecommunicationNumberPurposeType.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
