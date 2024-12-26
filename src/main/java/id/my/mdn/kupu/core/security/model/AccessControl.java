/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.security.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.persistence.SqlResultSetMappings;
import jakarta.persistence.Table;
import jakarta.persistence.TableGenerator;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author aphasan
 */
@Entity
@Table(name = "SECURITY_ACCESSCONTROL")
@NamedQueries({
    @NamedQuery(name = "AccessControl.findByName",
            query = "SELECT acl FROM AccessControl acl WHERE acl.module = :module AND acl.name = :name"),
    @NamedQuery(name = "AccessControl.getList",
            query = "SELECT acl, gac "
                    + "FROM AccessControl acl LEFT JOIN acl.applyingGroups gac "
                    + "ON gac.securityGroup = :securityGroup "
                    + "WHERE acl.module = :module")
})
@SqlResultSetMappings({
    @SqlResultSetMapping(
            name = "AccessControl",
            classes = {
                @ConstructorResult(
                        targetClass = AccessControl.class,
                        columns = {
                            @ColumnResult(name = "id", type = Long.class),
                            @ColumnResult(name = "module_name", type = String.class),
                            @ColumnResult(name = "name", type = String.class),
                            @ColumnResult(name = "description", type = String.class),
                            @ColumnResult(name = "active", type = Boolean.class)
                        }
                )
            }
    )
})
public class AccessControl implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @TableGenerator(name = "Security_AccessControl", table = "KEYGEN", allocationSize = 1)
    @GeneratedValue(generator = "Security_AccessControl", strategy = GenerationType.TABLE)
    private Long id;
    
    @Column(name = "MODULE_NAME", length = 255, nullable = false)
    @NotNull
    private String module;
    
    @Column(length = 255, nullable = false)
    @NotNull
    private String name;    
    
    private String description;
    
    @Transient
    private boolean active = false;

    @OneToMany(mappedBy = "accessControl", cascade = CascadeType.ALL)
    private List<GroupAccessControl> applyingGroups;

    public AccessControl() {
    }

    public AccessControl(Long id, String module_name, String name, String description, boolean active) {
        this.id = id;
        this.module = module_name;
        this.name = name;
        this.description = description;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<GroupAccessControl> getApplyingGroups() {
        return applyingGroups;
    }

    public void setApplyingGroups(List<GroupAccessControl> applyingGroups) {
        this.applyingGroups = applyingGroups;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AccessControl other = (AccessControl) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }
    
}
