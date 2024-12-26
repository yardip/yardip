/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package id.my.mdn.kupu.core.security.dao;

import id.my.mdn.kupu.core.base.util.Result;
import id.my.mdn.kupu.core.base.util.FilterTypes.FilterData;
import id.my.mdn.kupu.core.party.dao.AbstractPartyRoleFacade;
import id.my.mdn.kupu.core.party.dao.PartyRoleTypeFacade;
import id.my.mdn.kupu.core.party.entity.Organization;
import id.my.mdn.kupu.core.security.model.ApplicationSecurityGroup;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.From;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Stateless
public class ApplicationSecurityGroupFacade extends AbstractPartyRoleFacade<ApplicationSecurityGroup> {
    
    @Inject
    private EntityManager em;

    @Inject
    private PartyRoleTypeFacade roleTypeFacade;

    public ApplicationSecurityGroupFacade() {
        super(ApplicationSecurityGroup.class);
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
        Join<ApplicationSecurityGroup, Organization> party = cb.treat(
                role.join("party"), Organization.class);

        cq.select(role);

        return new From[]{role, party};
    }

    @Override
    protected Predicate applyFilter(String filterName, Object filterValue, 
            CriteriaQuery cq, From... from) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        switch (filterName) {
            case "module":
                return cb.equal(from[0].get("module"), (String) filterValue);
            case "groupname":
                return cb.equal(from[1].get("name"), (String) filterValue);
            default:
                return super.applyFilter(filterName, filterValue, cq, from);
        }
    }

    @Override
    public Result<String> create(ApplicationSecurityGroup entity) {
        return super.create(entity);
    }
    
    public boolean isNameAlreadyExist(String module, String groupname) {
        List<ApplicationSecurityGroup> findAll = findAll(
                List.of(
                        new FilterData("module", module),
                        new FilterData("groupname", groupname)
                )
        );
        return !findAll.isEmpty();
    }

    public ApplicationSecurityGroup findByName(String module, String groupname) {
        List<ApplicationSecurityGroup> findAll = findAll(
                List.of(
                        new FilterData("module", module),
                        new FilterData("groupname", groupname)
                )
        );
        return !findAll.isEmpty() ? findAll.get(0) : null;
    }
    
}
