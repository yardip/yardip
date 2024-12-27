/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.core.hr.view;

import id.my.mdn.kupu.core.base.util.RequestedView;
import id.my.mdn.kupu.core.base.view.ChildPage;
import id.my.mdn.kupu.core.base.view.annotation.Bookmarked;
import id.my.mdn.kupu.core.base.view.annotation.Creator;
import id.my.mdn.kupu.core.base.view.annotation.Deleter;
import id.my.mdn.kupu.core.base.view.annotation.Editor;
import id.my.mdn.kupu.core.hr.entity.Position;
import id.my.mdn.kupu.core.hr.view.admin.EmploymentEditorPage;
import id.my.mdn.kupu.core.hr.view.widget.EmploymentFilter;
import id.my.mdn.kupu.core.hr.view.widget.EmploymentList;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Named(value = "employmentPage")
@ViewScoped
public class EmploymentPage extends ChildPage implements Serializable {    
    
    @Bookmarked
    @Inject
    private EmploymentList employmentList;

    @PostConstruct
    @Override
    protected void init() {
        super.init();
    }

    @Creator(of = "employmentList")
    public void openEmploymentCreator() {
        
        RequestedView creatorPage = gotoChild(EmploymentEditorPage.class);

        Position position = employmentList.getFilter().<EmploymentFilter>getContent().getPosition();
        creatorPage.addParam("pos").withValues(position);

        creatorPage.open();
    }

    @Editor(of = "employmentList")
    public void openEmploymentEditor() {
        
        RequestedView creatorPage = gotoChild(EmploymentEditorPage.class);

        creatorPage.addParam("entity").withValues(employmentList.getSelection());

        creatorPage.open();
    }

    @Deleter(of = "employmentList")
    public void openEmploymentDeleter() {
        employmentList.deleteSelections();
    }

    public EmploymentList getEmploymentList() {
        return employmentList;
    }
    
}
