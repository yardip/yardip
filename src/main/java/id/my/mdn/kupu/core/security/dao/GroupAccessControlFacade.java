/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package id.my.mdn.kupu.core.security.dao;

import id.my.mdn.kupu.core.base.dao.AbstractSqlFacade;
import id.my.mdn.kupu.core.base.util.QueryHelper;
import id.my.mdn.kupu.core.base.util.QueryParameter;
import id.my.mdn.kupu.core.security.model.AccessControl;
import id.my.mdn.kupu.core.security.model.ApplicationSecurityGroup;
import id.my.mdn.kupu.core.security.model.GroupAccessControl;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Stateless
public class GroupAccessControlFacade extends AbstractSqlFacade<AccessControl> {

    private static final String FIND_ALL
            = """
            SELECT AC0.ID, AC0.MODULE_NAME, AC0.NAME, AC0.DESCRIPTION, (GAC1.ACCESSCONTROL_ID IS NOT NULL) AS ACTIVE
            FROM SECURITY_ACCESSCONTROL AS AC0
            LEFT JOIN (
                SELECT GAC0.ACCESSCONTROL_ID
                FROM SECURITY_GROUPACCESSCONTROL AS GAC0
                WHERE GAC0.SECURITYGROUP_ID = ?
            ) AS GAC1
            ON AC0.ID = GAC1.ACCESSCONTROL_ID
              """;

    @Inject
    private EntityManager em;

    @Inject
    private AccessControlFacade aclFacade;

    @Inject
    private ApplicationSecurityGroupFacade groupFacade;

    @Inject
    private QueryHelper helper;

    public GroupAccessControlFacade() {
        super(AccessControl.class);
    }

    @Override
    protected String getFindAllQuery() {
        return FIND_ALL;
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    protected void setParameters(Query q, Map<String, Object> parameters) {
        q.setParameter(1, ((ApplicationSecurityGroup) parameters.get("securityGroup")).getId());
    }

    public void addAccess(ApplicationSecurityGroup group, AccessControl acl) {
        acl = aclFacade.find(acl.getId());
        group = groupFacade.find(group.getId());

        GroupAccessControl gac = findAccess(group, acl);
        if (gac == null) {
            gac = new GroupAccessControl();

            gac.setSecurityGroup(group);
            group.getAccessControls().add(gac);

            gac.setAccessControl(acl);
            acl.getApplyingGroups().add(gac);

            getEntityManager().persist(gac);
//            aclFacade.edit(acl);

            getEntityManager().flush();
//            
            getEntityManager().refresh(acl);
        }
    }

    public void removeAccess(ApplicationSecurityGroup group, AccessControl acl) {
        group = groupFacade.find(group.getId());
        acl = aclFacade.find(acl.getId());

        GroupAccessControl gac = findAccess(group, acl);
        getEntityManager().remove(gac);

        getEntityManager().flush();

        getEntityManager().refresh(group);
        getEntityManager().refresh(acl);
    }

    public void addAccesses(ApplicationSecurityGroup group, List<AccessControl> acls) {
        acls.stream().forEach(acl -> addAccess(group, acl));
    }

    public GroupAccessControl findAccess(ApplicationSecurityGroup group, AccessControl acl) {
        GroupAccessControl gac = helper.queryOne(
                getEntityManager(),
                "GroupAccessControl.find",
                GroupAccessControl.class,
                new QueryParameter("group", group),
                new QueryParameter("acl", acl)
        );

        return gac;
    }

}
