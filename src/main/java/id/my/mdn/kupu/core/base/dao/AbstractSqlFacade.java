/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.core.base.dao;

import id.my.mdn.kupu.core.base.util.FilterTypes.FilterData;
import id.my.mdn.kupu.core.base.util.Result;
import id.my.mdn.kupu.core.base.view.widget.IValueList.SorterData;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.persistence.Query;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author aphasan
 * @param <T>
 */
public abstract class AbstractSqlFacade<T extends Serializable> extends AbstractFacade<T> {

    protected static String COMPARE_EQUALS = " = ";
    protected static String COMPARE_LIKE = " LIKE ";

    public static interface QueryGenerator {

        String get();
    }

    public AbstractSqlFacade(Class<T> entityClass) {
        super(entityClass);
    }

    @Override
    public List<T> findAll(final Integer startPosition, final Integer maxResult,
            final Map<String, Object> parameters, final List<FilterData> filters,
            final List<SorterData> sorters, final List<T> defaultReturn,
            final DefaultChecker defaultChecker) {
        return findAll(this::getFindAllQuery, startPosition, maxResult, parameters,
                filters, sorters, defaultReturn, defaultChecker);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public List<T> findAll(final QueryGenerator queryGenerator,
            final Integer startPosition, final Integer maxResult,
            final Map<String, Object> parameters, final List<FilterData> filters,
            final List<SorterData> sorters, final List<T> defaultReturn,
            final DefaultChecker defaultChecker) {
        String filter = applyFilters(filters);

        DefaultChecker usedDefaultChecker
                = (defaultChecker != null) ? defaultChecker : (() -> shouldReturnDefault(filters));

        if (usedDefaultChecker.passed() && defaultReturn != null) {
            return defaultReturn;
        }

        QueryGenerator usedQueryGenerator = (queryGenerator != null
                ? queryGenerator : (this::getFindAllQuery));

        Query q = getEntityManager().createNativeQuery(
                Stream.of(usedQueryGenerator.get(), filter, orderBy(sorters))
                        .collect(Collectors.joining(" ")).trim(),
                entityClass.getSimpleName());

        setParameters(q, parameters);

        q.setFirstResult(startPosition);
        q.setMaxResults(maxResult);

        return q.getResultList();
    }

    protected void setParameters(Query q, Map<String, Object> parameters) {
    }

    @Override
    public Long countAll(final Map<String, Object> parameters,
            final List<FilterData> filters, final Long defaultReturn,
            final DefaultChecker defaultChecker) {
        return countAll(this::getCountAllQuery, parameters, filters, defaultReturn,
                defaultChecker);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Long countAll(final QueryGenerator queryGenerator,
            final Map<String, Object> parameters, final List<FilterData> filters,
            final Long defaultCount, final DefaultChecker defaultChecker) {
        String filter = applyFilters(filters);

        DefaultChecker usedDefaultChecker
                = (defaultChecker != null) ? defaultChecker : (() -> shouldReturnDefault(filters));

        if (usedDefaultChecker.passed() && defaultCount != null) {
            return defaultCount;
        }

        QueryGenerator usedQueryGenerator = (queryGenerator != null
                ? queryGenerator : (this::getCountAllQuery));

        Query q = getEntityManager()
                .createNativeQuery(Stream.of("SELECT COUNT(*) FROM (" + usedQueryGenerator.get(), filter, ")")
                        .collect(Collectors.joining(" ")).trim());

        setParameters(q, parameters);

        return (Long) q.getSingleResult();
    }

    protected String applyFilters(List<FilterData> filters) {
        if (filters != null && !filters.isEmpty()) {

            List<String> conditions = new ArrayList<>();
            filters.stream().forEach(data -> {
                String filter = applyFilter(data);
                if (filter != null && !filter.isEmpty()) {
                    conditions.add(filter);
                }
            });

            StringBuilder strConditions = new StringBuilder(
                    conditions.stream().filter(c -> c != null && !c.isEmpty()).collect(Collectors.joining(" AND ")).trim()
            );

            if (strConditions.length() != 0) {
                strConditions.insert(0, " ").insert(0, getFilterClause());
            }
            return strConditions.toString();
            
        } else {
            return "";
        }

    }

    protected String getFilterClause() {
        return "WHERE";
    }

    protected String orderBy(List<SorterData> orderSpecs) {
        if (orderSpecs != null && !orderSpecs.isEmpty()) {
            List<String> normalizedOrderSpecs = orderSpecs.stream()
                    .map(spec -> {
//                        String[] tokens = spec.split("\\W+");
                        String normalizedFieldName = translateOrderField(spec.field);
                        return normalizedFieldName != null ? normalizedFieldName + " " + spec.order : null;
                    })
                    .filter(spec -> spec != null)
                    .collect(Collectors.toList());
            String combinedOrderSpecs = normalizedOrderSpecs.stream().collect(Collectors.joining(", "));
            return "ORDER BY " + combinedOrderSpecs;
        } else {
            return "";
        }
    }

    protected String translateOrderField(String fieldName) {
        return fieldName;
    }

    protected String applyFilter(FilterData filterData) {
        return null;
    }

    protected abstract String getFindAllQuery();

    protected String getCountAllQuery() {
        return getFindAllQuery();
    }

    @Override
    public T find(Object id) {
        if(id == null) return null;
        List<T> result = findAll(List.of(new FilterData("id", id)));
        if (result.isEmpty() || result.size() > 1) {
            return null;
        } else {
            return result.get(0);
        }
    }

    protected T findNative(Object id) {
        return super.find(id);
    }

    @Override
    public Result<String> remove(T entity) {
        return super.remove(findNative(getId(entity)));
    }

    protected String replaceParameterizedPositions(String sqlTemplate, String... params) {
        SubStringIdx[] parameterizedPositions = findParameterizedPositions(sqlTemplate);
        StringBuilder buffer = new StringBuilder(sqlTemplate);
        for (int i = parameterizedPositions.length - 1; i >= 0; i--) {
            var parameterizedPosition = parameterizedPositions[i];
            buffer.replace(parameterizedPosition.start, parameterizedPosition.stop, params[i]);
        }
        return buffer.toString();
    }

    private SubStringIdx[] findParameterizedPositions(String sqlTemplate) {
        var pattern = Pattern.compile("\\<\\?\\>");
        var matcher = pattern.matcher(sqlTemplate);
        var found = new ArrayList<SubStringIdx>();
        while (matcher.find()) {
            found.add(new SubStringIdx(matcher.start(), matcher.end()));
        }
        return found.toArray(new SubStringIdx[found.size()]);
    }        

    private static final class SubStringIdx {

        public int start;
        public int stop;

        public SubStringIdx(int start, int stop) {
            this.start = start;
            this.stop = stop;
        }
    }

}
