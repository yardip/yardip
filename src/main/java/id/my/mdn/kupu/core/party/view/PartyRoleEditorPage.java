/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.party.view;

import id.my.mdn.kupu.core.base.view.FormPage;
import id.my.mdn.kupu.core.party.entity.PartyRole;
import id.my.mdn.kupu.core.party.dao.AbstractPartyRoleFacade;

/**
 *
 * @author aphasan
 * @param <T>
 */
public abstract class PartyRoleEditorPage<T extends PartyRole> extends FormPage<T> {
    
    public abstract AbstractPartyRoleFacade getRoleFacade();
}
