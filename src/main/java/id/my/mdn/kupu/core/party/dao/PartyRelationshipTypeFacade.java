/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package id.my.mdn.kupu.core.party.dao;

import id.my.mdn.kupu.core.base.dao.AbstractFacade;
import id.my.mdn.kupu.core.party.entity.PartyRelationship;
import id.my.mdn.kupu.core.party.entity.PartyRelationshipType;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Stateless
public class PartyRelationshipTypeFacade extends AbstractFacade<PartyRelationshipType> {
    
    @Inject
    private EntityManager em;

    public PartyRelationshipTypeFacade() {
        super(PartyRelationshipType.class);
    }   

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public void createTypeIfNotExist(Class<? extends PartyRelationship> relationshipClass, String name) {
        PartyRelationshipTypeFacade.this.createTypeIfNotExist(relationshipClass.getSimpleName(), name);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void createTypeIfNotExist(String roleClassName, String name) {
        PartyRelationshipType relationshipType = find(roleClassName);
        if (relationshipType == null) {
            relationshipType = new PartyRelationshipType();
            relationshipType.setId(roleClassName);
            relationshipType.setName(name);
            create(relationshipType);
        }
    }

    
}
