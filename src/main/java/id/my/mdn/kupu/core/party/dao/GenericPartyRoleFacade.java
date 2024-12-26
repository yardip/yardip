/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package id.my.mdn.kupu.core.party.dao;

import id.my.mdn.kupu.core.party.entity.Party;
import id.my.mdn.kupu.core.party.entity.PartyRole;
import java.util.List;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Stateless
public class GenericPartyRoleFacade {

    @Inject
    private EntityManager em;

    public List<PartyRole> getRoles(Party party) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<PartyRole> cq = cb.createQuery(PartyRole.class);
        Root<Party> root = cq.from(Party.class);
        Join<Party, PartyRole> roles = root.join("roles");

        cq.select(roles)
                .where(
                        cb.equal(root.get("id"), party.getId())
                );

        return em.createQuery(cq).getResultList();
    }

}
