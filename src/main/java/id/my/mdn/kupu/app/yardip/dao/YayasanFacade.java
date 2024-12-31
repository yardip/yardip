/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB40/StatelessEjbClass.java to edit this template
 */
package id.my.mdn.kupu.app.yardip.dao;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Stateless
public class YayasanFacade {
    
    @Inject
    private EntityManager em;

    public boolean isYayasanExists() {
        
        String sql = """
                     SELECT PARTY_ID
                     FROM PARTYROLE
                     WHERE PARTYROLETYPE_ID = 'BusinessEntity'
                     INTERSECT
                     SELECT PARTY_ID
                     FROM PARTYROLE
                     WHERE PARTYROLETYPE_ID = 'ParentOrganization'
                     """;
        
        Query query = em.createNativeQuery(sql);
        
        try { 
            query.getSingleResult();
        } catch (Exception ex) {
            return false;
        }
        
        return true;
        
    } 
}
