/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.core.base.util;

/**
 *
 * @author Arief Prihasanto <aphasan at mdnx.dev>
 */
public class SubModuleInfo extends ModuleInfo {
    
    private final ModuleInfo parentModule;
    
    public SubModuleInfo(ModuleInfo parentModule, String label, 
            String appName, String pkgName, Integer ordinal, 
            boolean enabled, boolean visible) {
        super(label, appName, pkgName, ordinal, enabled, visible);
        this.parentModule = parentModule;
    }

    public ModuleInfo getParentModule() {
        return parentModule;
    }
    
}
