/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.core.base.view.widget;

import id.my.mdn.kupu.core.base.util.RequestUtil;
import id.my.mdn.kupu.core.base.util.RequestedView;
import id.my.mdn.kupu.core.base.view.Page;
import id.my.mdn.kupu.core.base.view.util.Backstack;
import java.util.Map;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
public abstract class PageNavigator {

    @Inject
    private Backstack backstack;

    protected abstract Class<? extends Page> pageMap(String pageId);

    protected abstract String getHome();

    public void open(String pageId, String currentView) {
        if (currentView != null && !currentView.isEmpty()) {
            backstack.push(new RequestedView(currentView));
        } else {
            backstack.clear();
        }
        Class cl = pageMap(pageId);
        if (cl == null) {
            home();
        } else {
            open(cl);
        }
    }

    public void open(String pageId, Page viewPage) {
        if (viewPage != null) {
            open(pageId, viewPage.getViewUrl().toString());
        }
    }

    protected void open(Class<? extends Page> pageClass) {
        RequestUtil.requestView(pageClass).open();
    }

    protected void open(Class<? extends Page> pageClass, Map<String, Object> parameters) {
        RequestedView requestedView = RequestUtil.requestView(pageClass);
        for (String key : parameters.keySet()) {
            requestedView.addParam(key).withValues(parameters.get(key));
        }
        requestedView.open();
    }

    public void home() {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        String homeView = ec.getRequestContextPath() + getHome();
        RequestedView index = new RequestedView(homeView);
        index.open();
    }

    public Backstack getBackstack() {
        return backstack;
    }
}
