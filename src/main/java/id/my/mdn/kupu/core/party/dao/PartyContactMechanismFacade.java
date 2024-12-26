/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.party.dao;

import id.my.mdn.kupu.core.base.dao.AbstractFacade;
import id.my.mdn.kupu.core.base.util.Result;
import id.my.mdn.kupu.core.party.entity.ContactMechanism;
import id.my.mdn.kupu.core.party.entity.ContactMechanismPurposeType;
import id.my.mdn.kupu.core.party.entity.ContactType;
import id.my.mdn.kupu.core.party.entity.Party;
import id.my.mdn.kupu.core.party.entity.PartyContactMechanism;
import id.my.mdn.kupu.core.party.entity.PartyContactMechanismPurpose;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.From;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.List;
import java.util.logging.Level;

/**
 *
 * @author aphasan
 */
@Stateless
public class PartyContactMechanismFacade extends AbstractFacade<PartyContactMechanism> {

    @Inject
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PartyContactMechanismFacade() {
        super(PartyContactMechanism.class);
    }

    @Override
    public Result<String> create(PartyContactMechanism entity) {
        Party party = getEntityManager().find(Party.class, entity.getParty().getId());
        ContactMechanism contactMechanism = entity.getContactMechanism();
        if (contactMechanism.getId() == null) {
            getEntityManager().persist(contactMechanism);
            entity.setContactMechanism(contactMechanism);
            entity.getId().setContactMechanism(contactMechanism.getId());
        }
        Result<String> create = super.create(entity);
        if (create.isSuccess()) {
            getEntityManager().refresh(party);
        }
        return create;
    }

    @Override
    public Result<String> remove(PartyContactMechanism entity) {
        ContactMechanism contactMechanism = entity.getContactMechanism();
        Result<String> remove = super.remove(entity);
        if (remove.isSuccess()) {
            getEntityManager().flush();
            remove.copy(removeIfUseless(contactMechanism));
        }

        return remove;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    private Result<String> removeIfUseless(ContactMechanism contactMechanism) {
        contactMechanism = getEntityManager()
                .find(ContactMechanism.class, contactMechanism.getId());
        if (contactMechanism != null) {

            try {
                getEntityManager().remove(contactMechanism);
            } catch (Exception ex) {
                LOG.log(Level.SEVERE, "{0}.remove() method failed to remove entity ", getClass().getCanonicalName());
                return new Result<>(false, "Penghapusan data gagal!");
            }
        }
        return new Result<>(true, "Data berhasil dihapus!");

    }

    @Override
    protected Predicate applyFilter(String filterName, Object filterValue, CriteriaQuery cq, From... from) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        switch (filterName) {
            case "party":
                return cb.equal(from[0].get("party"), filterValue);
            case "contactType":
                return cb.equal(from[0].get("contactMechanism").get("contactType"), filterValue);
            default:
                return null;
        }
    }

    public List<PartyContactMechanism> findAvailableContactMechanisms(Party p) {

        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<PartyContactMechanism> cq = cb.createQuery(PartyContactMechanism.class);

        Root<Party> party = cq.from(Party.class);
        Join<Party, PartyContactMechanism> partyContactMechanism = party.join("contactMechanisms");

        cq.select(partyContactMechanism).where(cb.equal(party, p));

        TypedQuery<PartyContactMechanism> q = getEntityManager().createQuery(cq);

        return q.getResultList();
    }

    public List<PartyContactMechanismPurpose> findAvailableContactPurpose(Party p) {

        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<PartyContactMechanismPurpose> cq = cb.createQuery(PartyContactMechanismPurpose.class);

        Root<Party> party = cq.from(Party.class);
        Join<Party, PartyContactMechanism> contactMechanisms = party.join("contactMechanisms");
        Join<PartyContactMechanism, PartyContactMechanismPurpose> purposes = contactMechanisms.join("purposes");

        cq.select(purposes).where(cb.equal(party, p));

        TypedQuery<PartyContactMechanismPurpose> q = getEntityManager().createQuery(cq);

        return q.getResultList();
    }

    public void makeSelectionAsDefault(PartyContactMechanism partyContactMechanism, ContactType contactType) {

        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<PartyContactMechanism> cq = cb.createQuery(entityClass);

        Root<PartyContactMechanism> root = cq.from(entityClass);
        cq.select(root).where(
                cb.equal(root.get("party"), partyContactMechanism.getParty()),
                cb.equal(root.get("contactMechanism").get("contactType"), contactType)
        );

        TypedQuery<PartyContactMechanism> q = getEntityManager().createQuery(cq);

        q.getResultList().stream()
                .peek(pcm -> {
                    if (pcm.equals(partyContactMechanism)) {
                        pcm.setDefaulted(!pcm.isDefaulted());
                    } else {
                        pcm.setDefaulted(false);
                    }
                }).forEach(this::edit);
    }

    public List<ContactMechanismPurposeType> findPurposeTypes(PartyContactMechanism contactMechanism) {

        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<ContactMechanismPurposeType> cq = cb.createQuery(ContactMechanismPurposeType.class);

        Root<PartyContactMechanismPurpose> partyContactMechanismPurpose = cq.from(PartyContactMechanismPurpose.class);

        cq.select(partyContactMechanismPurpose.get("purposeType"))
                .where(
                        cb.equal(
                                partyContactMechanismPurpose.get("partyContactMechanism"),
                                contactMechanism
                        )
                );

        TypedQuery<ContactMechanismPurposeType> q = getEntityManager().createQuery(cq);

        return q.getResultList();
    }

    public List<PartyContactMechanismPurpose> findContactPurposes(Party party) {

        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<PartyContactMechanismPurpose> cq = cb.createQuery(PartyContactMechanismPurpose.class);

        Root<PartyContactMechanism> partyContactMechanism = cq.from(PartyContactMechanism.class);
        Join<PartyContactMechanism, PartyContactMechanismPurpose> partyContactMechanismPurpose = partyContactMechanism.join("purposes");

        cq.select(partyContactMechanismPurpose.get("purposeType")).where(
                cb.equal(partyContactMechanism.get("party"), party)
        ).distinct(true);

        TypedQuery<PartyContactMechanismPurpose> q = getEntityManager().createQuery(cq);

        return q.getResultList();
    }

    public PartyContactMechanism findDefaultContactMechanism(Party party, ContactType contactType) {

        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<PartyContactMechanism> cq = cb.createQuery(entityClass);

        Root<PartyContactMechanism> root = cq.from(entityClass);
        cq.select(root).where(
                cb.equal(root.get("party"), party),
                cb.equal(root.get("contactMechanism").get("contactType"), contactType),
                cb.isTrue(root.get("defaulted"))
        );

        TypedQuery<PartyContactMechanism> q = getEntityManager().createQuery(cq);

        try {
            return q.getSingleResult();
        } catch (Exception ex) {
            return null;
        }
    }

    public PartyContactMechanism findContactMechanismByPurpose(Party p, ContactMechanismPurposeType purposeType) {

        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<PartyContactMechanism> cq = cb.createQuery(PartyContactMechanism.class);

        Root<Party> party = cq.from(Party.class);
        Join<Party, PartyContactMechanism> contactMechanisms = party.join("contactMechanisms");
        Join<PartyContactMechanism, PartyContactMechanismPurpose> purposes = contactMechanisms.join("purposes");

        cq.select(contactMechanisms).where(
                cb.equal(party, p),
                cb.equal(purposes.get("purposeType"), purposeType)
        );

        TypedQuery<PartyContactMechanism> q = getEntityManager().createQuery(cq);

        try {
            return q.getSingleResult();
        } catch (Exception ex) {
            return null;
        }
    }

    public PartyContactMechanism findExistingFor(PartyContactMechanism partyContactMechanism, ContactMechanismPurposeType contactMechanismPurposeType) {

        return null;
    }

}
