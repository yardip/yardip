/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.party.dao;

import id.my.mdn.kupu.core.base.dao.AbstractFacade;
import id.my.mdn.kupu.core.party.entity.PartyContactMechanismPurpose;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

/**
 *
 * @author aphasan
 */
@Stateless
public class PartyContactMechanismPurposeFacade extends AbstractFacade<PartyContactMechanismPurpose> {

    @Inject
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PartyContactMechanismPurposeFacade() {
        super(PartyContactMechanismPurpose.class);
    }

//    @Override
//    public PartyContactMechanismPurpose strToObj(String str) {
//        return find(new PartyContactMechanismPurposeId(EntityUtil.parseCompositeId(str)));
//    }
    
}
