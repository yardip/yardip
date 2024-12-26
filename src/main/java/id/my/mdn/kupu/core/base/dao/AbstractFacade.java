package id.my.mdn.kupu.core.base.dao;

import id.my.mdn.kupu.core.base.util.FilterTypes.FilterData;
import id.my.mdn.kupu.core.base.util.Result;
import id.my.mdn.kupu.core.base.view.widget.IValueList.SorterData;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.From;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Arief Prihasanto <aphasan at medinacom.id>
 * @param <T>
 */
public abstract class AbstractFacade<T> {

    protected static Logger LOG = Logger.getLogger("kupu");

    @FunctionalInterface
    public static interface DefaultChecker {

        boolean passed();
    }

    protected Class<T> entityClass;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    public T createTransient(Map<String, Object> params) {
        try {
            T entity = entityClass.getDeclaredConstructor().newInstance();
            if (params != null) {
                for (String key : params.keySet()) {
                    Field field = entityClass.getDeclaredField(key);
                    field.setAccessible(true);
                    field.set(entity, params.get(key));
                }
            }
            return entity;
        } catch (InstantiationException | IllegalAccessException
                | NoSuchFieldException | SecurityException
                | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException ex) {
            Logger.getLogger(AbstractFacade.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public Object getId(T entity) {
        try {
            Method getId = entityClass.getMethod("getId");
            return getId.invoke(entity);
        } catch (SecurityException | IllegalAccessException | NoSuchMethodException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(AbstractFacade.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Result<String> create(T entity) {
        try {
            getEntityManager().persist(entity);
            return new Result<>(true, "Data berhasil disimpan!");
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "{0}.create() method failed to persist entity ", getClass().getCanonicalName());
            return new Result<>(false, "Penyimpanan data gagal!");
        }
    }

    public Result<String> create(Collection<T> entities) {
        for (T entity : entities) {
            Result<String> create = create(entity);
            if (create.isNotSuccess()) {
                return create;
            }
        }
        return new Result<>(true, "Data berhasil disimpan!");
    }

    public Result<String> createIfNotExist(T entity, String compareAttribute) {
        try {
            T foundEntity;
            if (compareAttribute == null) {
                foundEntity = findSingleByAttribute(
                        "id",
                        entityClass.getMethod("getId")
                                .invoke(entity)
                );
            } else {
                foundEntity = findSingleByAttribute(compareAttribute,
                        entityClass.getMethod("get" + StringUtils.capitalize(compareAttribute)).invoke(entity));
            }

            if (foundEntity == null) {
                return create(entity);
            } else {
                return new Result<>(false, "Data telah ada!");
            }
        } catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException ex) {
            LOG.log(Level.SEVERE, getClass().getCanonicalName(), ex);
            return new Result<>(false, ex.getMessage());
        }
    }

    public Result<String> edit(T entity) {
        try {
            getEntityManager().merge(entity);
            return new Result<>(true, "Data berhasil diperbarui!");
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "{0}.edit() method failed to merge entity ", getClass().getCanonicalName());
            return new Result<>(false, "Pembaruan data gagal!");
        }
    }

    public Result<String> edit(Collection<T> entities) {
        for (T entity : entities) {
            Result<String> edit = edit(entity);
            if (edit.isNotSuccess()) {
                return edit;
            }
        }
        return new Result<>(true, "Data berhasil diperbarui!");
    }

    public Result<String> remove(T entity) {
        try {
            getEntityManager().remove(getEntityManager().merge(entity));
            return new Result<>(true, "Data berhasil dihapus!");
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "{0}.remove() method failed to remove entity ", getClass().getCanonicalName());
            return new Result<>(false, "Penghapusan data gagal!");
        }
    }

    public Result<String> remove(Collection<T> entities) {
        for (T entity : entities) {
            Result<String> remove = remove(entity);
            if (remove.isNotSuccess()) {
                return remove;
            }
        }
        return new Result<>(true, "Data berhasil diperbarui!");
    }

    public T find(Object id) {
        if (id == null) {
            return null;
        }
        return getEntityManager().find(entityClass, id);
    }

    protected boolean shouldReturnDefault(List<FilterData> filters) {
        return filters == null || filters.isEmpty();
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public List<T> findAll(final Integer startPosition, final Integer maxResult, final Map<String, Object> parameters,
            final List<FilterData> filters, final List<SorterData> sorters, final List<T> defaultReturn, DefaultChecker defaultChecker) {

        if (defaultChecker == null) {
            defaultChecker = () -> shouldReturnDefault(filters);
        }

        if (defaultChecker.passed() && defaultReturn != null) {
            return defaultReturn;
        }

        CriteriaQuery cq = findQuery(filters, sorters);

        return executeFind(startPosition, maxResult, cq);
    }

    public List<T> findAll(final Integer startPosition, final Integer maxResult, final Map<String, Object> parameters,
            final List<FilterData> filters, final List<SorterData> sorters, final List<T> defaultReturn) {
        return this.findAll(startPosition, maxResult, parameters, filters, sorters, defaultReturn, null);
    }

    public List<T> findAll(final Integer startPosition, final Integer maxResult, final Map<String, Object> parameters,
            final List<FilterData> filters, final List<SorterData> sorters) {

        return findAll(startPosition, maxResult, parameters, filters, sorters, null);
    }

    public List<T> findAll(final Integer startPosition, final Integer maxResult,
            final List<FilterData> filters, final List<SorterData> sorters) {

        return findAll(startPosition, maxResult, null, filters, sorters, null);
    }

    public List<T> findAll(final Integer startPosition, final Integer maxResult, final Map<String, Object> parameters, final List<FilterData> filters) {
        return findAll(startPosition, maxResult, parameters, filters, null);
    }

    public List<T> findAll(final Integer startPosition, final Integer maxResult, final List<FilterData> filters) {
        return findAll(startPosition, maxResult, null, filters, null);
    }

    public List<T> findAll(final Map<String, Object> parameters, final List<FilterData> filters,
            final List<SorterData> sorters, final List<T> defaultReturn) {
        return findAll(0, 0, parameters, filters, sorters, defaultReturn);
    }

    public List<T> findAll(final List<FilterData> filters,
            final List<SorterData> sorters, final List<T> defaultReturn) {
        return findAll(0, 0, null, filters, sorters, defaultReturn);
    }

    public List<T> findAll(final Map<String, Object> parameters, final List<FilterData> filters, final List<SorterData> sorters) {
        return findAll(parameters, filters, sorters, null);
    }

    public List<T> findAll(final List<FilterData> filters, final List<SorterData> sorters) {
        return findAll(null, filters, sorters, null);
    }

    public List<T> findAll(final Map<String, Object> parameters, final List<FilterData> filters) {
        return findAll(parameters, filters, null);
    }

    public List<T> findAll(final Map<String, Object> parameters) {
        return findAll(parameters, null);
    }

    public List<T> findAll(final List<FilterData> filters) {
        return findAll(null, filters, null);
    }

    public List<T> findAll(final FilterData... filters) {
        return findAll(null, List.of(filters), null);
    }

    public List<T> findAll() {
        return findAll(0, 0, null, null, null, null);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Long countAll(final Map<String, Object> parameters, final List<FilterData> filters, final Long defaultCount, DefaultChecker defaultChecker) {

        if (defaultChecker == null) {
            defaultChecker = () -> shouldReturnDefault(filters);
        }

        if (defaultChecker.passed() && defaultCount != null) {
            return defaultCount;
        }

        CriteriaQuery<Long> cq = countingQuery(parameters, filters);

        return executeCount(cq);
    }

    public Long countAll(final Map<String, Object> parameters, final List<FilterData> filters, final Long defaultReturn) {
        return this.countAll(parameters, filters, defaultReturn, null);
    }

    public Long countAll(final Map<String, Object> parameters, final Long defaultReturn) {
        return countAll(parameters, null, null);
    }

    public Long countAll(final List<FilterData> filters, final Long defaultReturn) {
        return countAll(null, filters, null);
    }

    public Long countAll(final Map<String, Object> parameters, final List<FilterData> filters) {
        return countAll(parameters, filters, null);
    }

    public Long countAll(final Map<String, Object> parameters) {
        return countAll(parameters, null, null);
    }

    public Long countAll(final List<FilterData> filters) {
        return countAll(null, filters);
    }

    public Long countAll() {
        return countAll(null, null, null);
    }

    public T findSingleByAttribute(String attribute, Object value) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery(entityClass);
        Root<T> root = cq.from(entityClass);
        cq.select(root).where(cb.equal(root.get(attribute), value));
        try {
            return (T) getEntityManager().createQuery(cq).getSingleResult();
        } catch (Exception ex) {
            return null;
        }
    }

    public T findSingleByAttributes(List<FilterData> attributes) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(entityClass);

        From[] roots = selectFind(cq, null);

        applyFilters(attributes, cq, roots);

        try {
            T obj = getEntityManager().createQuery(cq).getSingleResult();
            return obj;
        } catch (Exception ex) {
            return null;
        }
    }

    protected From[] selectFind(CriteriaQuery cq, Map<String, Object> parameters) {
        Root<T> root = cq.from(entityClass);
        cq.select(root);

        return new From[]{root};
    }

//    protected From[] selectFind(CriteriaQuery cq, Map<String, Object> parameters) {
//        return selectFind(cq, null);
//    }
    protected From[] selectCount(CriteriaQuery cq, Map<String, Object> parameters) {
        Root<T> root = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(root));

        return new From[]{root};
    }

//    protected From[] selectCount(CriteriaQuery cq, Map<String, Object> parameters) {
//        return selectCount(cq, null);
//    }
    protected List<Predicate> addStaticPredicates(List<Predicate> predicates, CriteriaQuery cq, From... from) {
        return predicates;
    }

    protected Expression orderExpression(String field, From... from) {
        return null;
    }

    protected Order orderMethod(String field, Boolean ascending, From... from) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        Expression orderExpression = orderExpression(field, from);
        if (orderExpression == null) {
            return null;
        }
        if (ascending) {
            return cb.asc(orderExpression);
        } else {
            return cb.desc(orderExpression);
        }
    }

    protected void orderBy(List<String> fields, Boolean ascending, CriteriaQuery cq, From... from) {
        List<Order> orders = new ArrayList<>();
        for (int i = 0; i < fields.size(); i++) {
            Order order = orderMethod(fields.get(i), ascending, from);
            if (order != null) {
                orders.add(order);
            }
        }

        cq.orderBy(orders);

    }

    protected void orderBy(List<SorterData> sorters, CriteriaQuery cq, From... from) {
        List<Order> orders = new ArrayList<>();
        for (SorterData sorter : sorters) {
            Order order = orderMethod(
                    sorter.field,
                    sorter.order.equals(SorterData.ASC),
                    from
            );

            if (order != null) {
                orders.add(order);
            }
        }

        cq.orderBy(orders);

    }

    protected CriteriaQuery findQuery(Map<String, Object> parameters, List<FilterData> filters, List<SorterData> sorters) {

        CriteriaQuery<T> cq = getEntityManager().getCriteriaBuilder().createQuery(entityClass);

        From[] roots = selectFind(cq, parameters);

        applyFilters(filters, cq, roots);

        if (sorters != null) {
            orderBy(sorters, cq, roots);
        }

        return cq;
    }

    protected CriteriaQuery findQuery(List<FilterData> filters, List<SorterData> sorters) {

        return findQuery(null, filters, sorters);
    }

    protected CriteriaQuery findQuery(List<FilterData> filters) {
        return findQuery(filters, null);
    }

    protected void applyFilters(List<FilterData> filters, CriteriaQuery cq, From... froms) {
        List<Predicate> predicates = new ArrayList<>();
        if (filters != null && !filters.isEmpty()) {

            predicates = filters.stream()
                    .map(key -> applyFilter(key.name, key.value, cq, froms))
                    .filter(predicate -> predicate != null)
                    .collect(Collectors.toList());

        }

        predicates = addStaticPredicates(predicates, cq, froms);

        if (!predicates.isEmpty()) {
            cq.where(predicates.toArray(new Predicate[predicates.size()]));
        }
    }

    protected Predicate applyFilter(String filterName, Object filterValue, CriteriaQuery cq, From... froms) {
        return null;
    }

    protected List<T> executeFind(Integer startPosition, Integer maxResult, CriteriaQuery cq) {

        TypedQuery<T> q = getEntityManager().createQuery(cq)
                .setFirstResult(startPosition).setMaxResults(maxResult);

        return postProcess(q.getResultList());
    }

    protected List<T> postProcess(final List<T> entities) {
        return entities;
    }

    protected Long executeCount(CriteriaQuery cq) {
        TypedQuery<Long> q = getEntityManager().createQuery(cq);

        Long count = q.getSingleResult();
        return count;
    }

    protected CriteriaQuery countingQuery(Map<String, Object> parameters, List<FilterData> filters) {

        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery(Long.class);

        From[] roots = selectCount(cq, parameters);

        applyFilters(filters, cq, roots);

        return cq;
    }

    protected CriteriaQuery countingQuery(List<FilterData> filters) {
        return countingQuery(null, filters);
    }

}
