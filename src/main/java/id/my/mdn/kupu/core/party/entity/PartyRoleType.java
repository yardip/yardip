/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.party.entity;

import jakarta.persistence.Entity;

@Entity
public class PartyRoleType extends RoleType {

    public PartyRoleType() {
        super();
    }

    public PartyRoleType(String id, String name) {
        super(id, name, "PartyRoleType");
    }
    
}
