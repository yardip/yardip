/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.local.service;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.Application;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 *
 * @author aphasan
 */
@Named(value = "activeLocale")
@SessionScoped
public class ActiveLocale implements Serializable {

    private Locale current;
    
    private List<Locale> available;
    
    @Inject
    private FacesContext context;
    
    @Inject
    private TranslationFacade translationFacade;
    
    @PostConstruct
    public void init() {
        Application app = context.getApplication();
        current = app.getViewHandler().calculateLocale(context);
        available = new ArrayList<>();
        available.add(app.getDefaultLocale());
        app.getSupportedLocales().forEachRemaining(available::add);
    }
    
    public void reload() {
        context.getPartialViewContext().getEvalScripts()
                .add("location.replace(location)");
    }

    public Locale getCurrent() {
        return current;
    }
    
    public String getLanguageTag() {
        return current.toLanguageTag();
    }
    
    public void setLanguageTag(String languageTag) {
        current = Locale.forLanguageTag(languageTag);
    }
    
    public List<Locale> getAvailable() {
        return available;
    }
    
    public String findTranslation(String key) {
        return translationFacade.findTranslation(key, current);
    }
    
}
