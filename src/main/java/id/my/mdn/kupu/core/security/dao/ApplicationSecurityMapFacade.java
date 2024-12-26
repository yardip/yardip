/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.security.dao;

import id.my.mdn.kupu.core.base.dao.AbstractSqlFacade;
import id.my.mdn.kupu.core.base.util.QueryHelper;
import id.my.mdn.kupu.core.base.util.QueryParameter;
import id.my.mdn.kupu.core.security.model.ApplicationSecurityGroup;
import id.my.mdn.kupu.core.security.model.ApplicationSecurityMap;
import id.my.mdn.kupu.core.security.model.ApplicationUser;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.util.Map;

/**
 *
 * @author Arief Prihasanto <aphasan at medinacom.id>
 */
@Stateless
public class ApplicationSecurityMapFacade extends AbstractSqlFacade<ApplicationSecurityGroup> {

    private static final String FINDALL
            = """
            SELECT GRP1.ID AS ID, GRP1.ORG_ID AS ORGID, GRP1.GROUPNAME AS GROUPNAME, (USR1.USER_ID IS NOT NULL) AS ACTIVE
            FROM (
                 SELECT GRP0.ID, ORG.ID AS ORG_ID, ORG.NAME AS GROUPNAME
                 FROM (SELECT GRP.ID, PR.PARTY_ID
                         FROM SECURITY_APPLICATIONSECURITYGROUP AS GRP
                         JOIN PARTY_PARTYROLE AS PR ON GRP.ID = PR.ID
                 ) AS GRP0
                 JOIN PARTY_ORGANIZATION AS ORG ON GRP0.PARTY_ID = ORG.ID
            ) AS GRP1
            LEFT JOIN (
                SELECT USER_ID, GROUP_ID
                FROM SECURITY_APPLICATIONUSER AS USR0
                JOIN SECURITY_APPLICATIONSECURITYMAP AS MAP0
                ON USR0.ID = MAP0.USER_ID
                WHERE USR0.ID = ?
            ) AS USR1
            ON GRP1.ID = USR1.GROUP_ID
            """;

    @Inject
    private EntityManager em;

    @Inject
    private ApplicationUserFacade userFacade;

    @Inject
    private QueryHelper helper;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ApplicationSecurityMapFacade() {
        super(ApplicationSecurityGroup.class);
    }

    @Override
    protected String getFindAllQuery() {
        return FINDALL;
    }

    @Override
    protected void setParameters(Query q, Map<String, Object> parameters) {
        q.setParameter(1, String.valueOf(parameters.get("user")));
    }

    public void addMembership(ApplicationUser user, ApplicationSecurityGroup group) {
        user = userFacade.find(user.getId());

        ApplicationSecurityMap map = findMembership(user, group);

        if (map == null) {
            map = new ApplicationSecurityMap();
            map.setGroup(group);
            map.setUser(user);

            user.getGroups().add(map);

            userFacade.edit(user);
        }
    }

    public void removeMembership(ApplicationUser user, ApplicationSecurityGroup group) {
        user = userFacade.find(user.getId());

        ApplicationSecurityMap map = findMembership(user, group);

        getEntityManager().remove(map);

        getEntityManager().refresh(user);
    }

    public ApplicationSecurityMap findMembership(ApplicationUser user, ApplicationSecurityGroup group) {
        ApplicationSecurityMap map = helper.queryOne(getEntityManager(),
                "ApplicationSecurityMap.find",
                ApplicationSecurityMap.class,
                new QueryParameter("group", group),
                new QueryParameter("user", user)
        );

        return map;
    }

}
