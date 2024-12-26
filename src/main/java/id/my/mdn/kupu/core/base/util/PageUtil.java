/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.base.util;

import id.my.mdn.kupu.core.local.view.util.DatabaseResourceBundle;
import jakarta.faces.context.FacesContext;
import java.util.ResourceBundle;

/**
 *
 * @author aphasan
 */
public class PageUtil {

    public static String compileMessage(String value, String... params) {
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle bundle = ResourceBundle.getBundle(
                DatabaseResourceBundle.class.getCanonicalName(),
                context.getViewRoot().getLocale());
        String message = bundle.containsKey(value) ? bundle.getString(value) : value;

        if (message == null || message.isEmpty()) {
            message = value;
        }

        int i = 0;
        if (params != null && params.length > 0) {
            for (String param : params) {
                message = message.replaceAll("\\{" + i + "\\}", param);
                i++;
            }
        }

        return message;
    }
}
