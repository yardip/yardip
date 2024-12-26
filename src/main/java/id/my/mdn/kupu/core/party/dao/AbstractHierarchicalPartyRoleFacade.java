/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.core.party.dao;

import id.my.mdn.kupu.core.base.dao.AbstractFacade;
import id.my.mdn.kupu.core.base.model.HierarchicalEntity;
import id.my.mdn.kupu.core.base.util.Result;
import id.my.mdn.kupu.core.base.util.FilterTypes.FilterData;
import id.my.mdn.kupu.core.base.view.widget.IValueList;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 * @param <T>
 */
public abstract class AbstractHierarchicalPartyRoleFacade<T extends PartyRole & HierarchicalEntity>
        extends AbstractFacade<T> {

    public AbstractHierarchicalPartyRoleFacade(Class<T> entityClass) {
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
            Logger.getLogger(AbstractHierarchicalPartyRoleFacade.class.getName()).log(Level.SEVERE, null, ex);
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
    public List<T> findAll(
            Integer startPosition,
            Integer maxResult,
            Map<String, Object> parameters,
            List<FilterData> filters,
            List<IValueList.SorterData> sorters,
            List<T> defaultReturn
    ) {

        if (defaultReturn != null
                && (filters == null || filters.isEmpty()
                || (filters.size() == 1 && filters.get(0).name.equals("parent")))) {
            return defaultReturn;
        }

        CriteriaQuery cq = findQuery(filters, sorters);

        return executeFind(startPosition, maxResult, cq);
    }

    @Override
    public Long countAll(Map<String, Object> parameters, List<FilterData> filters, Long defaultReturn) {

        if (defaultReturn != null
                && (filters == null || filters.isEmpty()
                || (filters.size() == 1 && filters.get(0).name.equals("parent")))) {
            return defaultReturn;
        }

        CriteriaQuery<Long> cq = countingQuery(filters);

        return executeCount(cq);
    }

    public List<T> getChildren(T parent) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery(entityClass);
        Root<T> root = cq.from(entityClass);
        cq.select(root);

        if (parent != null) {
            cq.where(cb.equal(
                    root.get("parent"), parent));
        } else {
            cq.where(cb.isNull(root.get("parent")));
        }

        TypedQuery<T> q = getEntityManager().createQuery(cq);

        return q.getResultList();
    }

    @Override
    protected void applyFilters(List<FilterData> filters, CriteriaQuery cq,
            From... froms) {
        List<Predicate> predicates = new ArrayList<>();
        if (filters != null && !filters.isEmpty()) {

            predicates = filters.stream()
                    .map(key -> applyFilterInternal(key.name,
                    key.value, cq, froms))
                    .filter(predicate -> predicate != null)
                    .collect(Collectors.toList());

        }

        predicates = addStaticPredicates(predicates, cq, froms);

        if (!predicates.isEmpty()) {
            cq.where(predicates.toArray(Predicate[]::new));
        }
    }

    private Predicate applyFilterInternal(String filterName, Object filterValue,
            CriteriaQuery cq, From... froms) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        switch (filterName) {
            case "parent" -> {
                if (filterValue != null) {
                    return cb.equal(froms[0].get("parent"), filterValue);
                } else {
                    return cb.isNull(froms[0].get("parent"));
                }
            }
            default -> {
                return applyFilter(filterName, filterValue, cq, froms);
            }
        }
    }

    @Override
    protected From[] selectFind(CriteriaQuery cq, Map<String, Object> parameters) {
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
        return switch (filterName) {
            default ->
                null;
        };
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
            getEntityManager().refresh(party);
            return create;
        }

    }

    @Override
    public Result<String> remove(T role) {
        try {
            role = getEntityManager().merge(role);
            Party party = role.getParty();
            party.getRoles().remove(role);
            role.setParty(null);

            getEntityManager().remove(role);

            postRemove(role);

            return removeWhenNoRolesLeft(party);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "{0}.remove() method failed to remove entity ", getClass().getCanonicalName());
            return new Result<>(false, "Penghapusan data gagal!");
        }
    }

    protected void postRemove(T entity) {
    }

    private Result<String> removeWhenNoRolesLeft(Party party) {
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

    public T findByPerson(Party person) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(entityClass);
        Root<T> users = cq.from(entityClass);

        cq.select(users)
                .where(cb.equal(users.get("party").get("id"), person.getId()));

        TypedQuery<T> q = getEntityManager().createQuery(cq);

        try {
            return q.getSingleResult();
        } catch (Exception ex) {
            return null;
        }
    }

}
