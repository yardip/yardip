/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.party.event;

import id.my.mdn.kupu.core.base.view.Page;
import id.my.mdn.kupu.core.party.entity.Party;
import id.my.mdn.kupu.core.party.entity.PartyRoleType;

/**
 *
 * @author aphasan
 */
public class GenericPartyRole {
    
    private final Page page;
    
    private final Party party;
    
    private final PartyRoleType roleType;
    
    private boolean needCreatorPage = false;

    public GenericPartyRole(Page page, Party party, PartyRoleType roleType) {
        this.page = page;
        this.party = party;
        this.roleType = roleType;
    }

    public Page getPage() {
        return page;
    }

    public Party getParty() {
        return party;
    }

    public PartyRoleType getRoleType() {
        return roleType;
    }

    public boolean isNeedCreatorPage() {
        return needCreatorPage;
    }

    public void setNeedCreatorPage(boolean needCreatorPage) {
        this.needCreatorPage = needCreatorPage;
    }
}
