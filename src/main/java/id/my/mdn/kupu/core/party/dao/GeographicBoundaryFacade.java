/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.party.dao;

import id.my.mdn.kupu.core.base.dao.AbstractFacade;
import id.my.mdn.kupu.core.base.util.FilterTypes.FilterData;
import id.my.mdn.kupu.core.party.entity.GeographicBoundary;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.From;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

/**
 *
 * @author aphasan
 * @param <T>
 */
public abstract class GeographicBoundaryFacade<T extends GeographicBoundary> extends AbstractFacade<T> {

    public GeographicBoundaryFacade(Class<T> entityClass) {
        super(entityClass);
    }

    @Override
    public Long countAll(List<FilterData> filters) {
        return countAll(filters, 0L);
    }

    @Override
    public List<T> findAll(Integer startPosition, Integer maxResult, List<FilterData> filters) {
        return findAll(startPosition, maxResult, null, filters, null, new ArrayList<>());
    }

    @Override
    protected Predicate applyFilter(String filterName, Object filterValue, CriteriaQuery cq, From... from) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        switch (filterName) {
            case "parent":
                T parent = (T) filterValue;
                return cb.equal(from[0].get("parent"), parent);
            default:
                return null;
        }
    }

    public T findByName(String name, GeographicBoundary parent) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(entityClass);
        Root<T> root = cq.from(entityClass);

        cq.select(root).where(
                cb.equal(root.get("parent"), parent),
                cb.equal(root.get("name"), name)
        );

        TypedQuery<T> q = getEntityManager().createQuery(cq);

        try {
            return q.getSingleResult();
        } catch (Exception ex) {
            return null;
        }
    }

}
