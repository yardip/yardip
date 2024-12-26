/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.security.dao;

import id.my.mdn.kupu.core.base.util.Result;
import id.my.mdn.kupu.core.base.util.FilterTypes.FilterData;
import id.my.mdn.kupu.core.party.dao.AbstractPartyRoleFacade;
import id.my.mdn.kupu.core.party.dao.PartyRoleTypeFacade;
import id.my.mdn.kupu.core.party.entity.Person;
import id.my.mdn.kupu.core.security.model.AccessControl;
import id.my.mdn.kupu.core.security.model.ApplicationSecurityGroup;
import id.my.mdn.kupu.core.security.model.ApplicationSecurityMap;
import id.my.mdn.kupu.core.security.model.ApplicationUser;
import id.my.mdn.kupu.core.security.model.GroupAccessControl;
import id.my.mdn.kupu.core.security.util.PasswordUtil;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.From;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Arief Prihasanto <aphasan at medinacom.id>
 */
@Stateless
public class ApplicationUserFacade extends AbstractPartyRoleFacade<ApplicationUser> {

    @Inject
    private EntityManager em;

    @Inject
    private PartyRoleTypeFacade roleTypeFacade;
    
    @Inject
    ApplicationSecurityGroupFacade groupFacade;

    public ApplicationUserFacade() {
        super(ApplicationUser.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    protected PartyRoleTypeFacade getRoleTypeFacade() {
        return roleTypeFacade;
    }

    @Override
    protected From[] selectFind(CriteriaQuery cq, Map<String, Object> parameters) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        Root<ApplicationSecurityGroup> role = cq.from(entityClass);
        Join<ApplicationSecurityGroup, Person> party = cb.treat(
                role.join("party"), Person.class);

        cq.select(role);

        return new From[]{role, party};
    }

    @Override
    protected Predicate applyFilter(String filterName, Object filterValue,
            CriteriaQuery cq, From... from) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        switch (filterName) {
            case "username":
                return cb.equal(from[0].get("username"), (String) filterValue);
            default:
                return super.applyFilter(filterName, filterValue, cq, from);
        }
    }

    @Override
    public Result<String> create(ApplicationUser entity) {
        entity.setPassword(PasswordUtil.generate(entity.getPassword()));
        ApplicationSecurityGroup userGroup = groupFacade.findByName("security", "User");
        ApplicationSecurityMap securityMap = new ApplicationSecurityMap();
        securityMap.setUser(entity);
        securityMap.setGroup(userGroup);
        entity.getGroups().add(securityMap);
        return super.create(entity);
    }

    public Result<String> create(Person person, String username, String password, ApplicationSecurityGroup group) {
        ApplicationUser user = ApplicationUser.builder()
                .addToGroup(group)
                .withParty(person)
                .withUsername(username)
                .withPassword(password)
                .get();
        return create(user);
    }

    public ApplicationUser findByUsername(String username) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<ApplicationUser> cq = cb.createQuery(ApplicationUser.class);
        Root<ApplicationUser> users = cq.from(ApplicationUser.class);

        cq.select(users)
                .where(cb.equal(users.get("username"), username));

        TypedQuery<ApplicationUser> q = getEntityManager().createQuery(cq);

        try {
            return q.getSingleResult();
        } catch (Exception ex) {
            return null;
        }
    }

    public ApplicationUser findByUsernameAndPassword(String username, String password) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<ApplicationUser> cq = cb.createQuery(ApplicationUser.class);
        Root<ApplicationUser> users = cq.from(ApplicationUser.class);

        cq.select(users)
                .where(cb.equal(users.get("username"), username),
                        cb.equal(users.get("password"), PasswordUtil.generate(password))
                );

        TypedQuery<ApplicationUser> q = getEntityManager().createQuery(cq);

        try {
            return q.getSingleResult();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<ApplicationSecurityGroup> getGroups(ApplicationUser user) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<ApplicationSecurityGroup> cq = cb.createQuery(ApplicationSecurityGroup.class);

        Root<ApplicationSecurityGroup> groups = cq.from(ApplicationSecurityGroup.class);
        Join<ApplicationSecurityGroup, ApplicationSecurityMap> users = groups.join("users");

        cq.select(groups).where(cb.equal(users.get("user").get("id"), user.getId()));

        TypedQuery<ApplicationSecurityGroup> q = getEntityManager().createQuery(cq);

        return q.getResultList();
    }

    private List<AccessControl> getAccessControl(ApplicationSecurityGroup group) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<AccessControl> cq = cb.createQuery(AccessControl.class);

        Root<ApplicationSecurityGroup> groups = cq.from(ApplicationSecurityGroup.class);
        Join<ApplicationSecurityGroup, GroupAccessControl> gacls = groups.join("accessControls");

        cq.select(gacls.get("accessControl")).where(cb.equal(groups, group));

        TypedQuery<AccessControl> q = getEntityManager().createQuery(cq);

        return q.getResultList();
    }

    public Set<String> getGroupsAsString(ApplicationUser user) {

        Set<String> result = new HashSet<>();
        getGroups(user).forEach(group -> {
            getAccessControl(group).stream()
                    .map(acl -> acl.getName())
                    .forEach(name -> result.add(name));
        });
        return result;
    }

    public boolean isNameAlreadyExist(String username) {
        List<ApplicationUser> findAll = findAll(
                List.of(new FilterData("username", username)));
        return !findAll.isEmpty();
    }

    public boolean removeAppUser(Person person) {
        ApplicationUser appUser = findByParty(person);
        if(appUser != null) {
            remove(appUser);
            return true;
        }
        return false;
    }
}
