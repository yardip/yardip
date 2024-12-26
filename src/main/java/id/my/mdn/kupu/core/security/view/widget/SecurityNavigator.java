/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.core.security.view.widget;

import id.my.mdn.kupu.core.base.util.RequestedView;
import id.my.mdn.kupu.core.base.view.Page;
import id.my.mdn.kupu.core.base.view.widget.PageNavigator;
import id.my.mdn.kupu.core.security.dao.ApplicationUserFacade;
import id.my.mdn.kupu.core.security.model.ApplicationUser;
import id.my.mdn.kupu.core.security.view.ApplicationSecurityGroupPage;
import id.my.mdn.kupu.core.security.view.ApplicationUserPage;
import id.my.mdn.kupu.core.security.view.PasswordEditorPage;
import java.io.Serializable;
import java.security.Principal;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.security.enterprise.SecurityContext;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Named(value = "securityNavigator")
@ApplicationScoped
public class SecurityNavigator extends PageNavigator implements Serializable {

    @Inject
    private SecurityContext securityContext;

    @Inject
    private ApplicationUserFacade userFacade;

    @Override
    protected Class<? extends Page> pageMap(String pageId) {
        switch (pageId) {
            case "ApplicationUser":
                return ApplicationUserPage.class;
            case "ApplicationSecurityGroup":
                return ApplicationSecurityGroupPage.class;
            case "PasswordEditor":
                return PasswordEditorPage.class;
            default:
                return null;
        }
    }

    @Override
    public void open(String pageId, String currentView) {
        if (pageId.equals("PasswordEditor")) {
            Principal principal = securityContext.getCallerPrincipal();
            if (principal != null) {
                ApplicationUser user = userFacade.findByUsername(principal.getName());

                if (user != null) {
                    getBackstack().push(new RequestedView(currentView));
                    open(PasswordEditorPage.class);
                }
            }
        } else {
            super.open(pageId, currentView);
        }
    }

    @Override
    protected String getHome() {
        return "/core/security/view/applicationuser.xhtml";
    }

}
