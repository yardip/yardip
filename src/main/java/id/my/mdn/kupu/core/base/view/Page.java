package id.my.mdn.kupu.core.base.view;

import id.my.mdn.kupu.core.base.util.AnnotationProcessor;
import id.my.mdn.kupu.core.base.util.LogUtil;
import id.my.mdn.kupu.core.base.util.RequestUtil;
import id.my.mdn.kupu.core.base.util.RequestedView;
import id.my.mdn.kupu.core.base.view.annotation.Bookmarked;
import id.my.mdn.kupu.core.base.view.annotation.CreatorProcessor;
import id.my.mdn.kupu.core.base.view.annotation.DeleterProcessor;
import id.my.mdn.kupu.core.base.view.annotation.EditorProcessor;
import id.my.mdn.kupu.core.base.view.annotation.FilterProcessor;
import id.my.mdn.kupu.core.base.view.annotation.OnClose;
import id.my.mdn.kupu.core.base.view.annotation.OnPostApplyRequestValuesActions;
import id.my.mdn.kupu.core.base.view.annotation.OnPostInvokeApplicationActions;
import id.my.mdn.kupu.core.base.view.annotation.OnPostRestoreViewActions;
import id.my.mdn.kupu.core.base.view.annotation.OnPostUpdateModelValuesActions;
import id.my.mdn.kupu.core.base.view.annotation.OnPostValidateActions;
import id.my.mdn.kupu.core.base.view.annotation.OnPreApplyRequestValuesActions;
import id.my.mdn.kupu.core.base.view.annotation.OnPreInvokeApplicationActions;
import id.my.mdn.kupu.core.base.view.annotation.OnPreUpdateModelValuesActions;
import id.my.mdn.kupu.core.base.view.annotation.OnPreValidateActions;
import id.my.mdn.kupu.core.base.view.annotation.OnReload;
import id.my.mdn.kupu.core.base.view.annotation.PagingProcessor;
import id.my.mdn.kupu.core.base.view.annotation.SelectProcessor;
import id.my.mdn.kupu.core.base.view.util.Backstack;
import id.my.mdn.kupu.core.base.view.util.ConverterUtil;
import id.my.mdn.kupu.core.base.view.widget.IBookmarkable;
import id.my.mdn.kupu.core.security.util.RolesChecker;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.inject.spi.CDI;
import jakarta.faces.convert.Converter;
import jakarta.faces.event.ComponentSystemEvent;
import jakarta.inject.Inject;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.primefaces.PrimeFaces;

/**
 *
 * @author aphasan
 */
public abstract class Page implements IBookmarkable {

    @FunctionalInterface
    public static interface StatesSupplier {
        Map<String, List<String>> get();
    }

    @FunctionalInterface
    public static interface ContextSupplier {
        public Page get();
    }

    private RequestedView viewUrl;

    @Inject
    private Backstack backstack;

    @Inject
    private ReturnsProcessor returnsProcessor;

    private ContextSupplier contextSupplier = () -> this;

    private StatesSupplier statesSupplier = this::collectStates;
    
    @Inject
    private RolesChecker rolesChecker;
    
    private Set<String> acls = new HashSet<>();

    public boolean isUserHasRoles(String... roles) {
        if (roles.length == 0) {
            return true;
        }

        if (acls.contains("manage_universe")) {
            return true;
        }
        
        if(acls.containsAll(Set.of(roles))) {
            return true;
        }
        
        boolean result = true;
        
        for(String role : roles) {
            boolean hasRole = rolesChecker.isUserHasRoles(role);
            if(hasRole && !acls.contains(role)) {
                acls.add(role);
                continue;
            }
            result &= hasRole;
        }
        
        return result;
    }   

    protected void init() {
        saveUrl();
        doInit();
    }

    private void doInit() {

        SelectProcessor.process(this);
        PagingProcessor.process(this);
        FilterProcessor.process(this);
        CreatorProcessor.process(this);
        EditorProcessor.process(this);
        DeleterProcessor.process(this);

        returnsProcessor.addListener(this::onReturns);
    }

    public Page onReturns(int what, Object returns) {
        return this;
    }

    private void saveUrl() {
        String url = RequestUtil.getRequestURL(null, null);
        String[] splittedUrl = url.split("\\?");
        viewUrl = new RequestedView(splittedUrl[0]);
    }

    @Override
    public Map<String, List<String>> getStates() {
        return statesSupplier.get();
    }

    protected Map<String, List<String>> collectStates() {

        Map<String, List<String>> states = new HashMap<>();

        Arrays.asList(getClass().getDeclaredFields()).stream()
                .filter(field -> field.getAnnotation(Bookmarked.class) != null)
                .forEach(field -> {
                    try {
                        field.setAccessible(true);
                        Object fieldValue = field.get(this);

                        if (fieldValue != null) {
                            if (fieldValue instanceof IBookmarkable) {
                                states.putAll(((IBookmarkable) fieldValue).getStates());
                            } else {
                                if (fieldValue instanceof List) {
                                    if (!((List) fieldValue).isEmpty()) {
                                        String encodedString = RequestUtil.encode((List) fieldValue);
                                        if (encodedString != null) {
                                            states.put(field.getName(), Arrays.asList(encodedString));
                                        }
                                    }
                                } else {
                                    Bookmarked bookmarked = field.getAnnotation(Bookmarked.class);
                                    String name = bookmarked.name();
                                    String converterName = bookmarked.converter();
                                    String objAsStr;
                                    Converter converter = null;
                                    if (!converterName.isEmpty()) {
                                        converter = ConverterUtil.findConverter(CDI.current(), converterName);
                                    } else {
                                        converter = ConverterUtil.findConverter(CDI.current(), field.getType());
                                    }

                                    if (converter != null) {
                                        objAsStr = converter.getAsString(null, null, fieldValue);
                                    } else {
                                        objAsStr = fieldValue.toString();
                                    }
                                    name = !name.isEmpty() ? name : field.getName();
                                    states.put(name, Arrays.asList(objAsStr));
                                }
                            }
                        }
                    } catch (IllegalAccessException ex) {
                        Logger.getLogger(Page.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });

        Arrays.asList(getClass().getDeclaredMethods()).stream()
                .filter(m -> m.getAnnotation(Bookmarked.class) != null)
                .forEach(m -> {
                    try {
                        m.setAccessible(true);
                        Object ret = m.invoke(this, new Object[]{});
                        if (ret != null) {
                            states.putAll((Map<String, List<String>>) ret);
                        }
                    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                        Logger.getLogger(Page.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });

        return states;
    }

    public void updateAddressBar() {
        LogUtil.log("{0}.updateAddressBar",
                getClass().getCanonicalName());
        updateUrl();
        PrimeFaces.current().ajax().addCallbackParam("url", contextSupplier.get().getViewUrl().toString());
    }
    
    public void updateUrl(Object obj) {
        updateUrl();
    }

    public void updateUrl() {
        contextSupplier.get().getViewUrl().getParams().clear();
        Map<String, List<String>> states = contextSupplier.get().getStates();
        contextSupplier.get().getViewUrl().getParams().putAll(states);
    }

    private void pushUrlToBackstack() {
        backstack.push(contextSupplier.get().getViewUrl());
    }

    public RequestedView gotoChild(Class<? extends Page> clsPage) {
        updateUrl();
        pushUrlToBackstack();
        return RequestUtil.requestView(clsPage);
    }

    protected RequestedView gotoChildNoPush(Class<? extends Page> clsPage) {
        updateUrl();
        return RequestUtil.requestView(clsPage);
    }

    public void addParam(String key, String... values) {
        getViewUrl().getParams().put(key, Arrays.asList(values));
    }

    public void removeParam(String key) {
        getViewUrl().getParams().remove(key);
    }

    protected void clearReturns() {
        updateUrl();
        removeParam("returns");
        removeParam("what");
        getBackstack().push(getViewUrl());
    }

    @PreDestroy
    protected void preDestroy() {
        close();
    }

    protected void close() {
        AnnotationProcessor.execute(this, OnClose.class, null, new Object[]{});
    }
    
    public void onPostRestoreViewEvent(ComponentSystemEvent evt) {
        AnnotationProcessor.execute(this, OnPostRestoreViewActions.class, null, new Object[]{});
    }

    public void onPreApplyRequestValuesEvent(ComponentSystemEvent evt) {
        AnnotationProcessor.execute(this, OnPreApplyRequestValuesActions.class, null, new Object[]{});
    }

    public void onPostApplyRequestValuesEvent(ComponentSystemEvent evt) {
        AnnotationProcessor.execute(this, OnPostApplyRequestValuesActions.class, null, new Object[]{});
    }

    public void onPreValidateEvent(ComponentSystemEvent evt) {
        AnnotationProcessor.execute(this, OnPreValidateActions.class, null, new Object[]{});
    }

    public void onPostValidateEvent(ComponentSystemEvent evt) {
        AnnotationProcessor.execute(this, OnPostValidateActions.class, null, new Object[]{});
    }

    public void onPreUpdateModelValuesEvent(ComponentSystemEvent evt) {
        AnnotationProcessor.execute(this, OnPreUpdateModelValuesActions.class, null, new Object[]{});
    }

    public void onPostUpdateModelValuesEvent(ComponentSystemEvent evt) {
        AnnotationProcessor.execute(this, OnPostUpdateModelValuesActions.class, null, new Object[]{});
    }

    public void onPreInvokeApplicationEvent(ComponentSystemEvent evt) {
        AnnotationProcessor.execute(this, OnPreInvokeApplicationActions.class, null, new Object[]{});
    }

    public void onPostInvokeApplicationEvent(ComponentSystemEvent evt) {
        AnnotationProcessor.execute(this, OnPostInvokeApplicationActions.class, null, new Object[]{});
    }

    public void onPreRenderViewEvent(ComponentSystemEvent evt) {
        AnnotationProcessor.execute(this, OnReload.class, null, new Object[]{});
    }

    public void setContextSupplier(ContextSupplier contextSupplier) {
        this.contextSupplier = contextSupplier;
    }

    public void setStatesSupplier(StatesSupplier statesSupplier) {
        this.statesSupplier = statesSupplier;
    }

    public RequestedView getViewUrl() {
        return viewUrl;
    }

    public void setViewUrl(RequestedView viewUrl) {
        this.viewUrl = viewUrl;
    }

    public Backstack getBackstack() {
        return backstack;
    }

    public ReturnsProcessor getReturnsProcessor() {
        return returnsProcessor;
    }

    public void setReturnsProcessor(ReturnsProcessor returnsProcessor) {
        this.returnsProcessor = returnsProcessor;
    }
}
