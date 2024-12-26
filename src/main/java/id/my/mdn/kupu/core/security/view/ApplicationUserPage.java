/*
 * Copyright 2015 mbahbejo.
 * All rights reserved

 * A lot of time, effort and money is spent designing and implementing the software.
 * All system design, text, graphics, the selection and arrangement thereof, and
 * all software compilations, underlying source code, software and all other material
 * on this software are copyright mbahbejo and any affiliates.
 * 
 * In simple terms, every element of this software is protected by copyright.
 * Unless you have our express written permission, you are not allowed
 * to copy partially and or completely, modify partially and or completely,
 * use partially and or completely and or reproduce any part of this  software
 * in any way, shape and or form.
 * 
 * Taking material from other source code and or document mbahbejo and affiliates has designed is
 * also prohibited. You can be prosecuted by the licensee as well as by us as licensor.
 * 
 * Any other use of materials of this software, including reproduction for purposes other
 * than that noted in the business agreement, modification, distribution, or republication,
 * without the prior written permission of mbahbejo is strictly prohibited.
 * 
 * The source code, partially and or completely, shall not be presented and or shown
 * and or performed to public and or other parties without the prior written permission
 * of mbahbejo

 */
package id.my.mdn.kupu.core.security.view;

import id.my.mdn.kupu.core.base.view.ChildPage;
import id.my.mdn.kupu.core.base.view.annotation.Bookmarked;
import id.my.mdn.kupu.core.base.view.annotation.Creator;
import id.my.mdn.kupu.core.base.view.annotation.Deleter;
import id.my.mdn.kupu.core.base.view.annotation.Editor;
import id.my.mdn.kupu.core.security.view.widget.ApplicationSecurityUserList;
import java.io.Serializable;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 *
 * @author mbahbejo
 */
@Named(value = "applicationUserPage")
@ViewScoped
public class ApplicationUserPage extends ChildPage implements Serializable {

    @Inject
    @Bookmarked
    private ApplicationSecurityUserList dataView;

    @PostConstruct
    @Override
    public void init() {
        super.init();
    }

    public void openGroupView() {
        gotoChild(ApplicationSecurityGroupPage.class).open();
    }

    public void openGroupChooser() {
        gotoChild(ApplicationSecurityMapPage.class)
                .addParam("user")
                .withValues(dataView.getSelector().getSelection())
                .open();
    }

    @Creator(of = "dataView")
    public void create() {
        gotoChild(ApplicationUserEditorPage.class)
                .open();
    }

    @Editor(of = "dataView")
    public void edit() {
        gotoChild(PasswordEditorPage.class)
                .addParam("entity")
                .withValues(dataView.getSelector().getSelection())
                .open();
    }

    @Deleter(of = "dataView")
    public void delete() {
        dataView.delete(dataView.getSelections());
    }

    public ApplicationSecurityUserList getDataView() {
        return dataView;
    }

}
