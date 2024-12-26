/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.party.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

/**
 *
 * @author aphasan
 */
@Entity
@DiscriminatorValue("TelecommunicationNumber")
public class TelecommunicationNumberPurposeType extends ContactMechanismPurposeType {
    
}
