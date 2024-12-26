/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.base.view.widget;

import id.my.mdn.kupu.core.local.view.util.DatabaseResourceBundle;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.application.FacesMessage.Severity;
import jakarta.faces.context.FacesContext;
import java.util.ResourceBundle;

/**
 *
 * @author aphasan
 */
@ApplicationScoped
public class Toast {

    public static void error(String msg, String... params) {
        show(FacesMessage.SEVERITY_ERROR, msg, params);
    }

    public static void info(String msg, String... params) {
        show(FacesMessage.SEVERITY_INFO, msg, params);
    }

    public static void warning(String msg, String... params) {
        show(FacesMessage.SEVERITY_WARN, msg, params);
    }

    public static void fatal(String msg, String... params) {
        show(FacesMessage.SEVERITY_FATAL, msg, params);
    }

    private static void show(Severity severity, String value, String... params) {
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle bundle = ResourceBundle.getBundle(DatabaseResourceBundle.class.getCanonicalName());
        String message = bundle.containsKey(value) ? bundle.getString(value) : value;

        if (message == null || message.isEmpty()) {
            message = value;
        }

        int i = 0;
        for (String param : params) {
            message = message.replaceAll("\\{" + i + "\\}", param);
            i++;
        }

        context.addMessage(null, new FacesMessage(severity, message, message));
    }
}
