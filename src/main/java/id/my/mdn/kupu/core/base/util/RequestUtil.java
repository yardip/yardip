
package id.my.mdn.kupu.core.base.util;

import id.my.mdn.kupu.core.base.view.Page;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 *
 * @author aphasan
 */
public final class RequestUtil {

    public static String getRequestURL(String url, Map<String, List<String>> appendixes) {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletRequest rqs = (HttpServletRequest) ec.getRequest();

        String baseUrl = url != null ? url : rqs.getRequestURI();
        Map<String, List<String>> params = new HashMap<>();

        String queryString = rqs.getQueryString();
        if (queryString != null) {
            Arrays.asList(queryString.split("&"))
                    .stream().forEach(s -> {
                        String[] parsedString = s.split("=");
                        String key = parsedString[0];
                        List<String> value = null;
                        if (parsedString.length > 1) {
                            value = Arrays.asList(parsedString[1]);
                        }
                        params.put(key, null);

                    });
        }

        if (appendixes != null && !appendixes.isEmpty()) {
            params.putAll(appendixes);
        }

        String newUrl = encode(baseUrl, params);

        return newUrl;
    }

    public static void gotoView(String view, Map<String, List<String>> requestParams) {
        FacesContext fc = FacesContext.getCurrentInstance();  
        for(Entry entry : requestParams.entrySet()) {
            if(((List<String>)entry.getValue()).size() > 1) {
                requestParams.put(entry.getKey().toString(), List.of(encode((List<String>)entry.getValue())));
            }
        }
        String url = encode(view, requestParams);
        
        goToView(url);

    }

    public static void goToView(RequestedView requestedView) {
        gotoView(requestedView.getView(), requestedView.getParams());
    }

    public static void goToView(String url) {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        try {
            ec.redirect(url);
        } catch (IOException ex) {
            Logger.getLogger(RequestUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static RequestedView requestView(Class<? extends Page> clsPage) {
        return new RequestedView(clsPage);
    }

    public static void goToDashboard() {

        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();

        String url = new StringBuilder(ec.getRequestContextPath())
                .append("/index.xhtml")
                .toString();

        try {
            ec.redirect(url);
        } catch (IOException ex) {
            Logger.getLogger(RequestUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static Map<String, String> getViewParams() {
        Map<String, String> viewParams = new HashMap<>();
        FacesContext.getCurrentInstance()
                .getExternalContext()
                .getRequestParameterMap()
                .forEach((String k, String v) -> {
                    viewParams.put(k, v);
                });
        return viewParams;
    }

    public static String encode(String baseUrl, Map<String, List<String>> params) {
        FacesContext fc = FacesContext.getCurrentInstance();
        if (fc == null) {
            return null;
        }
        ExternalContext ec = fc.getExternalContext();

        String encodedUrl = ec.encodeRedirectURL(baseUrl.isBlank() ? "/" : baseUrl, params);

        return encodedUrl;
    }

    public static String encode(List<? extends Object> objs) {

        String encodedString = null;

        String objsToStr = objs.stream()
                .map(Object::toString)
                .collect(Collectors.joining("+"));
        try {
            encodedString = URLEncoder.encode(objsToStr, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(RequestUtil.class.getName()).log(Level.SEVERE, null, ex);
        }

        return encodedString;
    }

    public static String encode(String url) {
        FacesContext fc = FacesContext.getCurrentInstance();
        if (fc == null) {
            return null;
        }
        ExternalContext ec = fc.getExternalContext();

        String encodedUrl = ec.encodeRedirectURL(url.isBlank() ? "/" : url, null);

        return encodedUrl;
    }
}
