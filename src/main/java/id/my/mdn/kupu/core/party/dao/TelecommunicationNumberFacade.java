/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.party.dao;

import id.my.mdn.kupu.core.base.dao.AbstractFacade;
import id.my.mdn.kupu.core.party.entity.TelecommunicationNumber;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

/**
 *
 * @author aphasan
 */
@Stateless
public class TelecommunicationNumberFacade extends AbstractFacade<TelecommunicationNumber> {

    @Inject
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TelecommunicationNumberFacade() {
        super(TelecommunicationNumber.class);
    }

//    @Override
//    public TelecommunicationNumber strToObj(String str) {
//        return find(Long.parseLong(str));
//    }

}
