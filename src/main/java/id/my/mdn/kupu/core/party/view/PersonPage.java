/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.party.view;

import id.my.mdn.kupu.core.base.view.Page;
import id.my.mdn.kupu.core.base.view.annotation.Bookmarked;
import id.my.mdn.kupu.core.base.view.annotation.Creator;
import id.my.mdn.kupu.core.base.view.annotation.Deleter;
import id.my.mdn.kupu.core.base.view.annotation.Editor;
import id.my.mdn.kupu.core.party.view.widget.PersonList;
import java.io.Serializable;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 *
 * @author aphasan
 */
@Named(value = "personPage")
@ViewScoped
public class PersonPage extends Page implements Serializable {
    
    @Inject
    @Bookmarked
    private PersonList partyList;

    public PersonList getPartyList() {
        return partyList;
    }
    
    @Creator(of = "partyList")
    public void openCreator() {
        gotoChild(PartyEditorPage.class)
                .addParam("partyClass")
                .withValues("person")
                .open();
    }
    
    @Editor(of = "partyList")
    public void openEditor() {
        gotoChild(PersonDetailPage.class)
                .addParam("party")
                .withValues(partyList.getSelector().getSelection())
                .open();
    }
    
    @Deleter(of = "partyList")
    public void openDeleter() {
        
    }
    
}
