/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.party.dao;

import id.my.mdn.kupu.core.base.dao.AbstractFacade;
import id.my.mdn.kupu.core.party.entity.PostalAddressPurposeType;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

/**
 *
 * @author aphasan
 */
@Stateless
public class PostalAddressPurposeTypeFacade extends AbstractFacade<PostalAddressPurposeType>{

    @Inject
    private EntityManager em;

    public PostalAddressPurposeTypeFacade() {
        super(PostalAddressPurposeType.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
