/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.party.dao;

import id.my.mdn.kupu.core.base.dao.AbstractFacade;
import id.my.mdn.kupu.core.base.util.Result;
import id.my.mdn.kupu.core.party.entity.Party;
import id.my.mdn.kupu.core.party.entity.PartyRole;
import id.my.mdn.kupu.core.party.entity.Person;
import id.my.mdn.kupu.core.party.entity.PersonRole;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.From;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author aphasan
 * @param <T>
 */
public abstract class PersonRoleFacade<T extends PersonRole> extends AbstractFacade<T> {

    public PersonRoleFacade(Class<T> entityClass) {
        super(entityClass);
    }

    @Override
    public T createTransient(Map<String, Object> params) {
        PartyRole role = null;

        try {
            role = entityClass.getDeclaredConstructor().newInstance();
        } catch (NoSuchMethodException | SecurityException
                | InstantiationException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(PersonRoleFacade.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (role == null) {
            return null;
        }

        role.setId(0L);

        if (params != null) {
            Party party = (Party) params.get("party");
            if (party != null) {
                role.setParty(party);
                party.getRoles().add(role);
            } else {

            }
        }

        return entityClass.cast(role);
    }

    @Override
    protected From[] selectFind(CriteriaQuery cq, Map<String, Object> parameters) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        Root<T> role = cq.from(entityClass);
        Join<T, Person> person = cb.treat(role.join("party"), Person.class);

        cq.select(role);

        return new From[]{role, person};
    }

    @Override
    protected From[] selectCount(CriteriaQuery cq, Map<String, Object> parameters) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        Root<T> role = cq.from(entityClass);
        Join<T, Person> person = cb.treat(role.join("party"), Person.class);

        cq.select(cb.count(role));

        return new From[]{role, person};
    }

    @Override
    protected Predicate applyFilter(String filterName, Object filterValue, CriteriaQuery cq, From... from) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        switch (filterName) {
            case "name":
                if (filterValue.equals("")) {
                    return null;
                }

                return cb.or(
                        cb.like(cb.upper(from[1].get("firstName")),
                                ("%" + filterValue + "%").toUpperCase()),
                        cb.like(cb.upper(from[1].get("lastName")),
                                ("%" + filterValue + "%").toUpperCase())
                );
            default:
                return null;
        }
    }

    @Override
    public Result<String> create(T role) {

        Person person = (Person) role.getPerson();

        if (person.getId() == null) {
            try {
                getEntityManager().persist(person);
                return new Result<>(true, "Data berhasil disimpan!");
            } catch (Exception ex) {
                LOG.log(Level.SEVERE, "{0}.create() method failed to persist entity ", getClass().getCanonicalName());
                return new Result<>(false, "Penyimpanan data gagal!");
            }
        } else {
            person = getEntityManager().find(Person.class, person.getId());
            Result<String> create = super.create(role);
            if (create.isSuccess()) {
                getEntityManager().refresh(person);
            }
            return create;
        }
    }

    @Override
    public Result<String> remove(T entity) {
        Party party = entity.getParty();
        Result<String> remove = super.remove(entity);
        if (remove.isSuccess()) {
            getEntityManager().flush();
            remove.copy(removeIfUseless(party));
        }

        return remove;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    private Result<String> removeIfUseless(Party party) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<PartyRole> cq = cb.createQuery(PartyRole.class);
        Root<PartyRole> root = cq.from(PartyRole.class);
        cq.select(root).where(cb.equal(root.get("party"), party));
        TypedQuery<PartyRole> q = getEntityManager().createQuery(cq);

        List<PartyRole> roles = q.getResultList();

        if (roles.isEmpty()) {
            party = getEntityManager()
                    .find(Party.class, party.getId());

            try {
                getEntityManager().remove(party);
            } catch (Exception ex) {
                LOG.log(Level.SEVERE, "{0}.remove() method failed to remove entity ", getClass().getCanonicalName());
                return new Result<>(false, "Penghapusan data gagal!");
            }
        }
        return new Result<>(true, "Data berhasil dihapus!");
    }

}
