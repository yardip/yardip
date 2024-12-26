/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.base.dao;

import id.my.mdn.kupu.core.base.model.HierarchicalEntity;
import id.my.mdn.kupu.core.base.util.FilterTypes.FilterData;
import id.my.mdn.kupu.core.base.view.widget.IValueList.SorterData;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.From;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author aphasan
 * @param <T>
 */
public abstract class AbstractHierarchicalFacade<T extends HierarchicalEntity>
    extends AbstractFacade<T>{

    public AbstractHierarchicalFacade(Class<T> entityClass) {
        super(entityClass);
    }

    @Override
    public List<T> findAll(
            Integer startPosition,
            Integer maxResult,
            Map<String, Object> parameters,
            List<FilterData> filters,
            List<SorterData> sorters,
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
            cq.where(predicates.toArray(new Predicate[predicates.size()]));
        }
    }
    
    public T getRoot() {
        return null;
    }

    private Predicate applyFilterInternal(String filterName, Object filterValue, 
            CriteriaQuery cq, From... froms) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        switch (filterName) {
            case "parent": {
                if (filterValue != null) {
                    return cb.equal(froms[0].get("parent"), filterValue);
                } else {
                    return cb.isNull(froms[0].get("parent"));
                }
            }
            default:
                return applyFilter(filterName, filterValue, cq, froms);
        }
    }

}
