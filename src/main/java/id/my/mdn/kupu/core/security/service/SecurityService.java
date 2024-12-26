/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.security.service;

import id.my.mdn.kupu.core.base.util.ModuleInfo;
import id.my.mdn.kupu.core.party.dao.PartyFacade;
import id.my.mdn.kupu.core.party.entity.Organization;
import id.my.mdn.kupu.core.party.entity.Party;
import id.my.mdn.kupu.core.party.entity.Person;
import id.my.mdn.kupu.core.security.dao.AccessControlFacade;
import id.my.mdn.kupu.core.security.dao.ApplicationSecurityGroupFacade;
import id.my.mdn.kupu.core.security.dao.ApplicationUserFacade;
import id.my.mdn.kupu.core.security.model.AccessControl;
import id.my.mdn.kupu.core.security.model.ApplicationSecurityGroup;
import id.my.mdn.kupu.core.security.model.ApplicationSecurityMap;
import id.my.mdn.kupu.core.security.model.ApplicationUser;
import id.my.mdn.kupu.core.security.model.GroupAccessControl;
import id.my.mdn.kupu.core.security.util.PasswordUtil;
import jakarta.ejb.SessionContext;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonString;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Arief Prihasanto <aphasan at medinacom.id>
 */
@Stateless
public class SecurityService {
    
    @Inject
    private SessionContext ctx;
    
    @Inject
    private PartyFacade partyFacade;
    
    @Inject
    private AccessControlFacade aclFacade;
    
    @Inject
    private ApplicationUserFacade userFacade;
    
    @Inject
//    private UserGroupFacade groupFacade;
    private ApplicationSecurityGroupFacade groupFacade;
    
    public void changePassword(String newPassword) {
        if (newPassword != null && !newPassword.isEmpty()) {
            String hashedPassword = PasswordUtil.generate(newPassword);
            ApplicationUser user = getUser();
            user.setPassword(hashedPassword);
            userFacade.edit(user);
        }
    }
    
    public ApplicationUser getUser() {
        String username = ctx.getCallerPrincipal().getName();
        ApplicationUser user = userFacade.findSingleByAttribute("username", username);
        return user;
    }
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void dumpSecurity(ModuleInfo moduleInfo) {
        
        InputStream in = moduleInfo.getResourceAsStream("security.json");
        if (in != null) {
            try (JsonReader jsonReader = Json.createReader(in)) {
                JsonObject jsonSecurities = jsonReader.readObject();
                String moduleName = moduleInfo.getName();
                
                processAcls(moduleName, jsonSecurities);
                processGroups(moduleName, jsonSecurities);
                processUsers(moduleName, jsonSecurities);
            }
            
        }
    }
    
    private void processAcls(String module, JsonObject jsonSecurities) {
        JsonArray jsonAcls = jsonSecurities.getJsonArray("acls");
        if (jsonAcls == null) {
            return;
        }
        for (int i = 0; i < jsonAcls.size(); i++) {
            JsonObject jsonObj = jsonAcls.getJsonObject(i);
            processAcl(module, jsonObj);
        }
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    private void processAcl(String module, JsonObject jsonObj) {
        JsonObject jsonAcl = jsonObj.getJsonObject("acl");
        String name = jsonAcl.getString("name");
        String description = jsonAcl.getString("description");
        
        boolean aclExist = aclFacade.isAlreadyExist(module, name);
        
        if (!aclExist) {
            AccessControl acl = aclFacade.createTransient(null);
            
            acl.setModule(module);
            acl.setName(name);
            acl.setDescription(description);
            
            aclFacade.create(acl);
        }
        
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    private void processGroups(String module, JsonObject jsonSecurities) {
        JsonArray jsonGroups = jsonSecurities.getJsonArray("groups");
        if (jsonGroups == null) {
            return;
        }
        for (int i = 0; i < jsonGroups.size(); i++) {
            JsonObject jsonGroup = jsonGroups.getJsonObject(i);
            processGroup(module, jsonGroup);
        }
    }
    
    private void processGroup(String module, JsonObject jsonObj) {
        JsonObject jsonGroup = jsonObj.getJsonObject("group");
        
        String groupname = jsonGroup.getString("name");
        
        if (!groupFacade.isNameAlreadyExist(module, groupname)) {
            
            Organization org = Organization.builder().name(groupname).get();
            
            ApplicationSecurityGroup group = ApplicationSecurityGroup.builder()
                    .organization(org)
                    .get();
            
            group.getOrganization().setName(groupname);
            group.setModule(module);
            
            JsonArray jsonAcls = jsonGroup.getJsonArray("acls");
            if (jsonAcls != null) {
                List<GroupAccessControl> acls = jsonAcls.stream()
                        .map(value -> stringToAcl(((JsonString) value).getString(), module))
                        .map(acl -> {
                            GroupAccessControl gac = new GroupAccessControl();
                            gac.setSecurityGroup(group);
                            gac.setAccessControl(acl);
                            return gac;
                        })
                        .collect(Collectors.toList());
                group.setAccessControls(acls);
            }
            
            partyFacade.edit(org);
        }
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    private AccessControl stringToAcl(String name, String defaultModule) {
        
        String[] splittedName = name.split("\\.");
        String module = defaultModule;
        String strAcl = "";
        switch (splittedName.length) {
            case 1:
                strAcl = splittedName[0];
                break;
            case 2:
                module = splittedName[0];
                strAcl = splittedName[1];
                break;
            default:
                break;
        }
        
        AccessControl acl = aclFacade.findByName(module, strAcl);
        return acl;
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    private void processUsers(String module, JsonObject jsonSecurities) {
        JsonArray jsonUsers = jsonSecurities.getJsonArray("users");
        if (jsonUsers == null) {
            return;
        }
        for (int i = 0; i < jsonUsers.size(); i++) {
            JsonObject jsonUser = jsonUsers.getJsonObject(i);
            processUser(module, jsonUser);
        }
    }
    
    public ApplicationUser findByUsername(String username) {
        return userFacade.findByUsername(username);
    }
    
    public boolean isUsernameExists(String username) {
        return findByUsername(username) != null;
    }
    
    private void processUser(String module, JsonObject jsonObj) {
        JsonObject jsonUser = jsonObj.getJsonObject("user");
        
        String type = jsonUser.getString("type", "Person");
        String firstName = jsonUser.getString("first-name", null);
        String lastName = jsonUser.getString("last-name", null);
        String username = jsonUser.getString("username");
        String plainPassword = jsonUser.getString("password");
        List<ApplicationSecurityGroup> groups = jsonUser.getJsonArray("groups")
                .stream()
                .map(value -> ((JsonString) value).getString())
                .map(value -> stringToGroup(value, module))
                .collect(Collectors.toList());
        
        if (!isUsernameExists(username)) {
            createNewSecurityUser(firstName, lastName, username, plainPassword, groups);
        }
    }
    
    public void createNewSecurityUser(String firstName, String lastName,
            String username, String plainPassword,
            List<ApplicationSecurityGroup> groups) {
        Person person = new Person();
        person.setFirstName(firstName);
        person.setLastName(lastName);
        
        ApplicationUser user = ApplicationUser.builder().withParty(person).get();
        user.setUsername(username);
        user.setPassword(PasswordUtil.generate(plainPassword));
        
        groups.stream().forEach(group -> {
            user.setGroups(createSecurityMaps(user, groups));
        });
        
        partyFacade.create(person);
    }
    
    public void addPersonToSecurityUser(Person person,
            String username, String plainPassword,
            List<ApplicationSecurityGroup> groups) {
        
        ApplicationUser user = ApplicationUser.builder().withParty(person).get();
        user.setUsername(username);
        user.setPassword(PasswordUtil.generate(plainPassword));
        
        groups.stream().forEach(group -> {
            user.setGroups(createSecurityMaps(user, groups));
        });
    }
    
    private List<ApplicationSecurityMap> createSecurityMaps(ApplicationUser user, List<ApplicationSecurityGroup> groups) {
        return groups.stream().filter(sg -> sg != null)
                .map(sg -> {
                    ApplicationSecurityMap securityMap = new ApplicationSecurityMap();
                    securityMap.setUser(user);
                    securityMap.setGroup(sg);
                    sg.getUsers().add(securityMap);
                    return securityMap;
                }
                )
                .collect(Collectors.toList());
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    private ApplicationSecurityGroup stringToGroup(String name, String defaultModule) {
        String[] splittedName = name.split("\\.");
        String module = defaultModule;
        String groupname = "";
        switch (splittedName.length) {
            case 1:
                groupname = splittedName[0];
                break;
            case 2:
                module = splittedName[0];
                groupname = splittedName[1];
                break;
            default:
                break;
        }
        
        return groupFacade.findByName(module, groupname);
    }
    
    public boolean createLogin(Party party, String username, String password, String... groupnames) {
        if (!isUsernameExists(username)) {
            ApplicationUser user = ApplicationUser.builder().withParty(party).get();
            user.setUsername(username);
            user.setPassword(password);
            
            List<ApplicationSecurityGroup> groups = Arrays.asList(groupnames)
                    .stream()
                    .map(value -> stringToGroup(value, "security"))
                    .collect(Collectors.toList());
            
            groups.stream().forEach(group -> {
                user.setGroups(createSecurityMaps(user, groups));
            });
            
            userFacade.create(user);
            
            return true;
        }
        
        return false;
    }
    
    public boolean removeLogin(String username) {
        ApplicationUser user = findByUsername(username);
        if(user != null) {
            userFacade.remove(user);
            return true;
        }
        return false;
    }
}
