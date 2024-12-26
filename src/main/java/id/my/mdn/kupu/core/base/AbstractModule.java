/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.core.base;

import id.my.mdn.kupu.core.base.event.AppPostInitEvent;
import id.my.mdn.kupu.core.base.event.InitEvent;
import id.my.mdn.kupu.core.base.event.ModulePostInitEvent;
import id.my.mdn.kupu.core.base.util.ModuleUtil;
import id.my.mdn.kupu.core.base.util.SubModuleInfo;
import jakarta.enterprise.event.Event;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author aphasan
 */
public abstract class AbstractModule {

    @Inject
    @ModulePostInitEvent
    private Event<AbstractModule> modulePostInitEvent;

    private final Map<String, SubModuleInfo> modules = new HashMap<>();

    public void register(String label, String pkgName, int order, boolean enabled, boolean visible) {
        String appName = pkgName.substring(pkgName.lastIndexOf(".") + 1);
        modules.put(
                pkgName,
                new SubModuleInfo(
                        ModuleUtil.getModuleInfo(getClass()),
                        label,
                        appName,
                        pkgName,
                        order,
                        enabled,
                        visible
                )
        );
    }

    public List<SubModuleInfo> getSubModuleInfos() {
        return modules.values().stream()
                .filter(SubModuleInfo::isEnabled)
                .filter(SubModuleInfo::isVisible)
                .sorted((m1, m2) -> m1.getOrdinal().compareTo(m2.getOrdinal()))
                .collect(Collectors.toList());
    }

    protected abstract String getLabel();

    protected abstract int getOrder();

    protected boolean enabled() {
        return true;
    }

    protected boolean visible() {
        return true;
    }

    protected void init(@Observes @InitEvent Object obj) {
        init();
        ModuleUtil.register(
                getLabel(),
                getClass().getPackage().getName(),
                getOrder(),
                enabled(),
                visible()
        );
        
    }

    protected void init() {}

    protected void postInit(@Observes @AppPostInitEvent Object obj) {
        postInit();
        modulePostInitEvent.fireAsync(this);
    }

    protected void postInit() {
        
    }

}
