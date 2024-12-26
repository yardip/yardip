/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.core.party.dao;

import id.my.mdn.kupu.core.base.dao.AbstractFacade;
import id.my.mdn.kupu.core.base.util.Result;
import id.my.mdn.kupu.core.party.entity.Party;
import id.my.mdn.kupu.core.party.entity.PartyRole;
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
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 * @param <T>
 */
public abstract class AbstractPartyRoleFacade<T extends PartyRole> extends AbstractFacade<T> {

    public AbstractPartyRoleFacade(Class<T> entityClass) {
        super(entityClass);
    }

    protected abstract PartyRoleTypeFacade getRoleTypeFacade();

    @Override
    public T createTransient(Map<String, Object> params) {
        T role = null;

        try {
            role = entityClass.getDeclaredConstructor().newInstance();
        } catch (NoSuchMethodException | SecurityException
                | InstantiationException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(AbstractPartyRoleFacade.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (role == null) {
            return null;
        }

        role.setId(0L);
        role.setPartyRoleType(getRoleTypeFacade().find(entityClass.getClass().getSimpleName()));

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
        Join<T, Party> party = role.join("party");

        cq.select(role);

        return new From[]{role, party};
    }

    @Override
    protected From[] selectCount(CriteriaQuery cq, Map<String, Object> parameters) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        Root<T> role = cq.from(entityClass);
        Join<T, Party> party = role.join("party");

        cq.select(cb.count(role));

        return new From[]{role, party};
    }

    @Override
    protected Predicate applyFilter(String filterName, Object filterValue, CriteriaQuery cq, From... from) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        switch (filterName) {
            default:
                return null;
        }
    }

    @Override
    public Result<String> create(T entity) {

        entity.setPartyRoleType(getRoleTypeFacade().find(entityClass.getSimpleName()));

        Party party = entity.getParty();

        if (entity.getParty().getId() == null) {
            try {
                getEntityManager().persist(party);
                return new Result<>(true, "Data berhasil disimpan!");
            } catch (Exception ex) {
                LOG.log(Level.SEVERE, "{0}.create() method failed to persist entity ", getClass().getCanonicalName());
                return new Result<>(false, "Penyimpanan data gagal!");
            }
        } else {
            party = getEntityManager().find(Party.class, party.getId());
            Result<String> create = super.create(entity);
            if (create.isSuccess()) {
                getEntityManager().refresh(party);
            }
            return create;
        }

    }

    @Override
    public Result<String> remove(T entity) {
        entity = getEntityManager().merge(entity);
        Party party = entity.getParty();
        party.getRoles().remove(entity);
        entity.setParty(null);
        try {
            getEntityManager().remove(entity);
            return removeIfUseless(party);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "{0}.remove() method failed to remove entity ", getClass().getCanonicalName());
            return new Result<>(false, "Penghapusan data gagal!");
        }
    }

    private Result<String> removeIfUseless(Party party) {
        List<PartyRole> roles = getAllRoles(party);

        if (roles.isEmpty()) {
            try {
                getEntityManager().remove(party);
            } catch (Exception ex) {
                LOG.log(Level.SEVERE, "{0}.remove() method failed to remove entity ", getClass().getCanonicalName());
                return new Result<>(false, "Penghapusan data gagal!");
            }
        }
        return new Result<>(true, "Data berhasil dihapus!");
    }

    private List<PartyRole> getAllRoles(Party party) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<PartyRole> cq = cb.createQuery(PartyRole.class);
        Root<Party> root = cq.from(Party.class);
        Join<Party, PartyRole> roles = root.join("roles");

        cq.select(roles)
                .where(
                        cb.equal(root.get("id"), party.getId())
                );

        return getEntityManager().createQuery(cq).getResultList();
    }

    public T findByParty(Party party) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(entityClass);
        Root<T> users = cq.from(entityClass);

        cq.select(users)
                .where(cb.equal(users.get("party").get("id"), party.getId()));

        TypedQuery<T> q = getEntityManager().createQuery(cq);

        try {
            return q.getSingleResult();
        } catch (Exception ex) {
            return null;
        }
    }

}
