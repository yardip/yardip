/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.party.view;

import id.my.mdn.kupu.core.base.util.Result;
import id.my.mdn.kupu.core.base.view.FormPage;
import id.my.mdn.kupu.core.base.view.annotation.Bookmarked;
import id.my.mdn.kupu.core.party.dao.PartyFacade;
import id.my.mdn.kupu.core.party.entity.Organization;
import id.my.mdn.kupu.core.party.entity.Party;
import id.my.mdn.kupu.core.party.entity.Person;
import jakarta.faces.event.AjaxBehaviorEvent;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 *
 * @author aphasan
 */
@Named(value = "partyEditorPage")
@ViewScoped
public class PartyEditorPage extends FormPage<Party> {
    
    @Inject
    private PartyFacade partyFacade;

    @Bookmarked
    private String partyClass;   

    @Override
    protected Party newEntity() {
        Party party;
        
        if (partyClass.equals("organization")) {
            party = Organization.builder().get();
        } else {
            party = Person.builder().get();
        }
        
        return party;
    }

    public void changeModel(AjaxBehaviorEvent evt) {
        updateUrl();
        getViewUrl().open();
    }

    @Override
    protected  Result<String> save(Party entity) {
        return partyFacade.create(entity);
    }

    @Override
    protected  Result<String> edit(Party entity) {
        return partyFacade.edit(entity);
    }    

    public String getPartyClass() {
        return partyClass;
    }

    public void setPartyClass(String partyClass) {
        this.partyClass = partyClass;
    }
    
}
