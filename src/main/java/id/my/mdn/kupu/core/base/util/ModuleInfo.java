/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.core.base.util;

import java.io.InputStream;

/**
 *
 * @author aphasan
 */
public class ModuleInfo {
    
    private final String label;
    
    private final String name;
    
    private final String pkgName;
    
    private final Integer ordinal;
    
    private boolean enabled;
    
    private boolean visible;

    public ModuleInfo(String label, String appName, String pkgName, Integer ordinal, boolean enabled, boolean visible) {
        this.label = label;
        this.name = appName;
        this.pkgName = pkgName;
        this.ordinal = ordinal;
        this.enabled = enabled;
        this.visible = visible;
    }
    
    public InputStream getResourceAsStream(String resourceName) {
        String modifiedPkgName = this.getPkgName().replaceAll("\\.", "/");
        String resource = "/" + modifiedPkgName + "/" + resourceName;
        
        return getClass().getResourceAsStream(resource);
    }

    public String getLabel() {
        return label;
    }

    public String getName() {
        return name;
    }

    public String getPkgName() {
        return pkgName;
    }

    public Integer getOrdinal() {
        return ordinal;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
