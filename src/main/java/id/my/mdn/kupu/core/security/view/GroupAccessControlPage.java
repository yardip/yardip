/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.core.security.view;

import id.my.mdn.kupu.core.base.view.ChildPage;
import id.my.mdn.kupu.core.base.view.annotation.Bookmarked;
import id.my.mdn.kupu.core.security.model.AccessControl;
import id.my.mdn.kupu.core.security.model.ApplicationSecurityGroup;
import id.my.mdn.kupu.core.security.view.widget.GroupAccessControlList;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Named(value = "groupAccessControlPage")
@ViewScoped
public class GroupAccessControlPage extends ChildPage implements Serializable {

    @Inject
    @Bookmarked
    private GroupAccessControlList dataView;

    @Bookmarked
    private ApplicationSecurityGroup securityGroup;

    @PostConstruct
    @Override
    protected void init() {
        super.init();
    }

    @Override
    public void load() {
        dataView.setParameters(this::parameters);
    }

    private Map<String, Object> parameters() {

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("securityGroup", securityGroup);
        return parameters;
    }

    public ApplicationSecurityGroup getSecurityGroup() {
        return securityGroup;
    }

    public void setSecurityGroup(ApplicationSecurityGroup securityGroup) {
        this.securityGroup = securityGroup;
    }

    public GroupAccessControlList getDataView() {
        return dataView;
    }

    public void toggleActivation(AccessControl acl) {
        dataView.toggleActivation(acl, securityGroup);
    }
    
}
