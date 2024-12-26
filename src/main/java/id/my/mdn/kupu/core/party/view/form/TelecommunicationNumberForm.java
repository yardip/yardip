/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.party.view.form;

import id.my.mdn.kupu.core.base.util.Result;
import id.my.mdn.kupu.core.party.dao.PartyContactMechanismFacade;
import id.my.mdn.kupu.core.party.entity.ContactMechanismPurposeType;
import id.my.mdn.kupu.core.party.entity.PartyContactMechanism;
import id.my.mdn.kupu.core.party.entity.TelecommunicationNumber;
import id.my.mdn.kupu.core.party.entity.TelecommunicationNumberPurposeType;
import id.my.mdn.kupu.core.party.view.widget.TelecommunicationNumberPurposeTypeList;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author aphasan
 */
@Named(value = "telecommunicationNumberForm")
@Dependent
public class TelecommunicationNumberForm extends PartyContactMechanismForm<TelecommunicationNumber, TelecommunicationNumberPurposeType> {
    
    @Inject
    private TelecommunicationNumberPurposeTypeList purposeTypeList; 

    @Inject
    private PartyContactMechanismFacade contactFacade;

    @Override
    public void init(PartyContactMechanism entity) {
        super.init(entity);

        if (entity.getPurposes() != null && !entity.getPurposes().isEmpty()) {
            List<ContactMechanismPurposeType> purposeTypes
                    = contactFacade.findPurposeTypes(entity);
            setPurposes(purposeTypes.stream()
                    .map(pt -> (TelecommunicationNumberPurposeType) pt)
                    .collect(Collectors.toList()));
        }
    }

    @Override
    public TelecommunicationNumberPurposeTypeList getPurposeTypeList() {
        return purposeTypeList;
    }     

    @Override
    protected Result<String> checkFormComponentValidity() {
        Result<String> result = new Result<>(true, null);
        String contactNumber = ((TelecommunicationNumber) entity.getContactMechanism()).getContactNumber();
        result.success = (contactNumber == null || !contactNumber.isBlank());
        if (!result.success) {
            result.payload = "Nomor telekomunikasi kosong";
        }
        return result;
    }
    
}
