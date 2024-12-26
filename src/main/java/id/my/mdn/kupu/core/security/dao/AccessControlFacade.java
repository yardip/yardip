/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.security.dao;

import id.my.mdn.kupu.core.base.dao.AbstractFacade;
import id.my.mdn.kupu.core.base.util.QueryHelper;
import id.my.mdn.kupu.core.base.util.QueryParameter;
import id.my.mdn.kupu.core.base.util.FilterTypes.FilterData;
import id.my.mdn.kupu.core.security.model.AccessControl;
import id.my.mdn.kupu.core.security.model.GroupAccessControl;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.From;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

/**
 *
 * @author aphasan
 */
@Stateless
public class AccessControlFacade extends AbstractFacade<AccessControl> {

    @Inject
    private EntityManager em;

    @Inject
    private QueryHelper helper;

    public AccessControlFacade() {
        super(AccessControl.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public boolean isAlreadyExist(String module, String name) {
        AccessControl acl = findByName(module, name);
        return acl != null;
    }

    public AccessControl findByName(String module, String name) {
        AccessControl acl = helper.queryOne(em, "AccessControl.findByName", AccessControl.class,
                new QueryParameter("module", module),
                new QueryParameter("name", name));
        return acl;
    }

    @Override
    protected CriteriaQuery findQuery(List<FilterData> filters) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();

        CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
        Root<AccessControl> acl = cq.from(AccessControl.class);
        Join<AccessControl, GroupAccessControl> gac = acl.join("applyingGroups", JoinType.LEFT);
//        gac.on(cb.equal(gac.get("securityGroup"), filters.get("group")));
        
        cq.multiselect(acl, gac);
//                .where(cb.equal(acl.get("module"), filters.get("module")));

        applyFilters(filters, cq, acl, gac);


        return cq;
    }

    @Override
    protected List<AccessControl> executeFind(Integer startPosition, Integer maxResult, CriteriaQuery cq) {
        TypedQuery<Object[]> q = getEntityManager().createQuery(cq)
                .setFirstResult(startPosition)
                .setMaxResults(maxResult);
        
        return q.getResultList().stream()
                .map(arr -> {
                    AccessControl acl = (AccessControl) arr[0];
                    GroupAccessControl group = null;
                    if (arr.length == 2 && arr[1] != null) {
                        group = (GroupAccessControl) arr[1];
                    }
                    return activyAccessControl(acl, group);
                })
                .collect(Collectors.toList());
    }

//    @Override
//    protected CriteriaQuery countingQuery(Map<String, Object> filters) {
//        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
//
//        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
//        Root<AccessControl> acl = cq.from(AccessControl.class);
//        
//        cq.select(cb.count(acl)).where(cb.equal(acl.get("module"), filters.get("module")));
//
//        return cq;
//    }

    @Override
    protected Predicate applyFilter(String filterName, Object filterValue, CriteriaQuery cq, From... from) {
        if (filterValue == null) {
            return null;
        }
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        switch (filterName) {
            case "module":
                return cb.equal(from[0].get("module"), filterValue);
            case "group":
                return cb.equal(from[1].get("securityGroup"), filterValue);
            default:
                return null;
        }
    }

    private AccessControl activyAccessControl(AccessControl acl, GroupAccessControl group) {
        if (group != null) {
            acl.setActive(true);
        } else {
            acl.setActive(false);
        }
        return acl;
    }

}
