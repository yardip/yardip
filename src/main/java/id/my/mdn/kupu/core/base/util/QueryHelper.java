/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.base.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

/**
 *
 * @author aphasan
 */
@Stateless
public class QueryHelper {

    public <T> T queryOne(EntityManager entityManager, String queryName, Class<T> resultType, QueryParameter... params) {
        TypedQuery<T> query = entityManager.createNamedQuery(queryName, resultType);
        if (params != null && params.length != 0) {
            for (QueryParameter param : params) {
                query.setParameter(param.getName(), param.getValue());
            }
        }
        try {
            T result = query.getSingleResult();
            return result;
        } catch (Exception ex) {
            return null;
        }
    }

    public <T> T queryOne(EntityManager entityManager,String queryName, Class<T> resultType) {
        return queryOne(entityManager, queryName, resultType, new QueryParameter[]{});
    }

    public <T> List<T> queryAll(EntityManager entityManager, String queryName, Class<T> resultType, QueryParameter... params) {
        TypedQuery<T> query = entityManager.createNamedQuery(queryName, resultType);
        if (params != null && params.length != 0) {
            for (QueryParameter param : params) {
                query.setParameter(param.getName(), param.getValue());
            }
        }
        return query.getResultList();
    }

    public <T> List<T> queryAll(EntityManager entityManager, String queryName, Class<T> resultType) {
        return queryAll(entityManager, queryName, resultType, new QueryParameter[]{});
    }

    public <T> T getDefault(EntityManager entityManager, Class<T> entityClass) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(entityClass);
        Root<T> root = cq.from(entityClass);
        cq.select(root).where(cb.isTrue(root.get("defaulted")));
        
        TypedQuery<T> query = entityManager.createQuery(cq);
        
        try {
            T result = query.getSingleResult();
            return result;
        } catch (Exception ex) {
            return null;
        }
    }

    private <T> void setDefault(EntityManager entityManager, T entity, boolean defaulted) {
        try {
            Method setDefault = entity.getClass().getMethod("setDefaulted", Boolean.class);
            setDefault.invoke(entity, defaulted);
            entityManager.merge(entity);
        } catch (SecurityException | IllegalArgumentException | NoSuchMethodException | IllegalAccessException | InvocationTargetException ex) {
            Logger.getLogger(QueryHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public <T> void defaulted(EntityManager entityManager, T newDefault) {
        T prevDefault = getDefault(entityManager, (Class<T>) newDefault.getClass());
        if (prevDefault != null) {
            setDefault(entityManager, prevDefault, false);
            entityManager.merge(prevDefault);
        }
        setDefault(entityManager, newDefault, true);
        entityManager.merge(newDefault);
    }

}
