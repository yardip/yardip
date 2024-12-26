/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.party.dao;

import id.my.mdn.kupu.core.base.dao.AbstractFacade;
import id.my.mdn.kupu.core.party.entity.ElectronicAddressPurposeType;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

/**
 *
 * @author aphasan
 */
@Stateless
public class ElectronicAddressPurposeTypeFacade extends AbstractFacade<ElectronicAddressPurposeType>{

    @Inject
    private EntityManager em;

    public ElectronicAddressPurposeTypeFacade() {
        super(ElectronicAddressPurposeType.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
