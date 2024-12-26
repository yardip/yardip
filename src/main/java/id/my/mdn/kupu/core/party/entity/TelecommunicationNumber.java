/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.party.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.io.Serializable;

/**
 *
 * @author aphasan
 */
@Entity
@Table(name = "PARTY_TELECOMMUNICATIONNUMBER")
public class TelecommunicationNumber extends ContactMechanism implements Serializable {

    private static final long serialVersionUID = 1L;

    private String countryCode;

    private String areaCode;

    private String contactNumber;

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    @Override
    public String getValue() {
        StringBuilder sb = new StringBuilder();
        if (countryCode != null && !countryCode.isEmpty()) {
            sb.append(countryCode);
            sb.append(" ");
        }
        if (areaCode != null && !areaCode.isEmpty()) {
            sb.append(areaCode);
            sb.append(" ");
        }
        sb.append(contactNumber);

        return sb.toString();
    }

}
