/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.core.base;

import id.my.mdn.kupu.core.base.util.ModuleInfo;
import id.my.mdn.kupu.core.base.util.ModuleUtil;
import jakarta.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 *
 * @author aphasan
 */
public abstract class AbstractApplication {

    private List<Locale> availableLocales;

    protected void init() {
        FacesContext context = FacesContext.getCurrentInstance();
        jakarta.faces.application.Application app = context.getApplication();

        availableLocales = new ArrayList<>();
        availableLocales.add(app.getDefaultLocale());
        app.getSupportedLocales().forEachRemaining(availableLocales::add);
    }

    public List<ModuleInfo> getModuleInfos() {
        List<ModuleInfo> modules = ModuleUtil.getModules().values().stream()
                .filter(ModuleInfo::isEnabled)
                .filter(ModuleInfo::isVisible)
                .sorted((m1, m2) -> m1.getOrdinal().compareTo(m2.getOrdinal()))
                .collect(Collectors.toList());

        return modules;
    }

    public List<Locale> getAvailableLocales() {
        return availableLocales;
    }

}
