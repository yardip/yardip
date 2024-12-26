/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.core.party.entity;

import jakarta.persistence.MappedSuperclass;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@MappedSuperclass
public class OrganizationRole extends PartyRole {    
    
    public Organization getOrganization() {
        return (Organization) getParty();
    }
    
    public void setPerson(Organization organization) {
        setParty(organization);
    }
}
