/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.local.view.util;

import id.my.mdn.kupu.core.local.service.TranslationFacade;
import jakarta.enterprise.inject.spi.CDI;
import jakarta.faces.context.FacesContext;
import java.io.IOException;
import java.util.Enumeration;
import java.util.ListResourceBundle;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.ResourceBundle.Control;

/**
 *
 * @author aphasan
 */
public class DatabaseResourceBundle extends ResourceBundle {

    private static final Control CONTROL = new DatabaseControl();

    @Override
    protected Object handleGetObject(String key) {
        return getCurrentInstance().getObject(key);
    }

    @Override
    public Enumeration<String> getKeys() {
        return getCurrentInstance().getKeys();
    }

    private ResourceBundle getCurrentInstance() {
        FacesContext context = FacesContext.getCurrentInstance();
        String key = CONTROL.getClass().getName();
        return (ResourceBundle) context.getAttributes()
                .computeIfAbsent(
                        key,
                        k -> ResourceBundle.getBundle(
                                key,
                                context.getViewRoot().getLocale(),
                                Thread.currentThread().getContextClassLoader(),
                                CONTROL)
                );
    }

    private static class DatabaseControl extends Control {

        @Override
        public ResourceBundle newBundle(String baseName, Locale locale,
                String format, ClassLoader loader, boolean reload)
                throws IllegalAccessException, InstantiationException,
                IOException {

            FacesContext context = FacesContext.getCurrentInstance();

            final Object[][] contents = CDI.current()
                    .select(TranslationFacade.class).get()
                    .getContent(locale, context.getApplication().getDefaultLocale());

            return new ListResourceBundle() {
                @Override
                protected Object[][] getContents() {
                    return contents;
                }
            };
        }

    }

}
