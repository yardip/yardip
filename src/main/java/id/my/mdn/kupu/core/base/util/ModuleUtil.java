package id.my.mdn.kupu.core.base.util;

import id.my.mdn.kupu.core.base.view.Page;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author aphasan
 */
public final class ModuleUtil {

    private static final String ROOT_PKG = "id.my.mdn.kupu";
    private static final Pattern PAGE_PATTERN;

    static {

        PAGE_PATTERN = Pattern.compile("(?:" + ROOT_PKG.replaceAll("\\.", "\\\\.") + ")(?:(?<viewdir>(?:\\.[a-z]+)+)(?:(?<view>\\.[A-Z]\\w+)Page)?)?");
    }

    private static final Map<String, ModuleInfo> modules = new HashMap<>();

//    public static List<ModuleInfo> getModuleInfos() {
//        List<ModuleInfo> ret = new ArrayList<>();
//        ret.addAll(modules.values());
//        return ret;
//    }

    public static Map<String, ModuleInfo> getModules() {
        return modules;
    }

    public static void register(String label, String pkgName, int order, boolean enabled, boolean visible) {
        String appName = pkgName.substring(pkgName.lastIndexOf(".") + 1);
        modules.put(
                pkgName, 
                new ModuleInfo(
                        label, 
                        appName, 
                        pkgName, 
                        order, 
                        enabled, 
                        visible
                )
        );
    }

    public static void inspectModules(Consumer<ModuleInfo>... callbacks) {

        modules.values().stream()
                .forEach(info -> {
                    Arrays.asList(callbacks).stream()
                            .forEach(callback -> callback.accept(info));
                });

    }

    public static String getPage(Page page) {

        Class<? extends Page> pageClass = page.getClass();

        return getPage(pageClass);
    }

    public static String getPage(Class<? extends Page> pageClass) {

        Matcher matcher = PAGE_PATTERN.matcher(pageClass.getCanonicalName());
        if (matcher.matches()) {
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            String viewdir = matcher.group("viewdir");
            String view = matcher.group("view");
            return ec.getRequestContextPath() + (viewdir + view).replaceAll("\\.", "/").toLowerCase() + ".xhtml";
        } else {
            return null;
        }

    }
    
    public static ModuleInfo getModuleInfo(Class cls) {
        
        String pkg = cls.getPackage().getName();

        ModuleInfo foundModuleInfo = null;

        while (!pkg.isEmpty()) {
            foundModuleInfo = modules.get(pkg);
            if (foundModuleInfo != null) {
                break;
            }

            int lastIndex = pkg.lastIndexOf(".");

            if (lastIndex != -1) {
                pkg = pkg.substring(0, lastIndex);
            } else {
                break;
            }
        }

        return foundModuleInfo;
    }

    public static String getConfigPage(Page page) {

        ModuleInfo foundModuleInfo = getModuleInfo(page.getClass());

        return foundModuleInfo != null ? foundModuleInfo.getName() : null;

    }
}
