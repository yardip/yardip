/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.security.view;

import id.my.mdn.kupu.core.base.view.ChildPage;
import id.my.mdn.kupu.core.base.view.annotation.Bookmarked;
import id.my.mdn.kupu.core.security.model.ApplicationSecurityGroup;
import id.my.mdn.kupu.core.security.model.ApplicationUser;
import id.my.mdn.kupu.core.security.view.widget.ApplicationSecurityMapList;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.Map;

/**
 *
 * @author aphasan
 */
@Named(value = "applicationSecurityMapPage")
@ViewScoped
public class ApplicationSecurityMapPage extends ChildPage implements Serializable {

    @Inject
    @Bookmarked
    private ApplicationSecurityMapList dataView;
    
    @Bookmarked
    private ApplicationUser user;

    @PostConstruct
    @Override
    protected void init() {
        super.init();
        dataView.setParameters(() -> Map.of("user", user));
    }   
    
    public void toggleMembership(ApplicationSecurityGroup group) {
        dataView.toggleMembership(group, user);
    }

    public ApplicationSecurityMapList getDataView() {
        return dataView;
    }

    public ApplicationUser getUser() {
        return user;
    }

    public void setUser(ApplicationUser user) {
        this.user = user;
    }

}
