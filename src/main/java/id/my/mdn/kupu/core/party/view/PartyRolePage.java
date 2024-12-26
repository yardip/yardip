/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.core.party.view;

import id.my.mdn.kupu.core.base.view.Page;
import id.my.mdn.kupu.core.party.entity.Party;
import id.my.mdn.kupu.core.security.view.ApplicationUserEditorPage;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
public abstract class PartyRolePage extends Page {
    
    public void createLogin() {
        gotoChild(ApplicationUserEditorPage.class)
                .addParam("party")
                .withValues(getContext())
                
                .open();
    }

    protected abstract Party getContext();
}
