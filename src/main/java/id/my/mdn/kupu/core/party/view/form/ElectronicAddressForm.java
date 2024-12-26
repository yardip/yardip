/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.party.view.form;

import id.my.mdn.kupu.core.base.util.Result;
import id.my.mdn.kupu.core.party.dao.PartyContactMechanismFacade;
import id.my.mdn.kupu.core.party.entity.ContactMechanismPurposeType;
import id.my.mdn.kupu.core.party.entity.ElectronicAddress;
import id.my.mdn.kupu.core.party.entity.ElectronicAddressPurposeType;
import id.my.mdn.kupu.core.party.entity.PartyContactMechanism;
import id.my.mdn.kupu.core.party.view.widget.ElectronicAddressPurposeTypeList;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author aphasan
 */
@Named(value = "electronicAddressForm")
@Dependent
public class ElectronicAddressForm extends PartyContactMechanismForm<ElectronicAddress, ElectronicAddressPurposeType> {

    @Inject
    private ElectronicAddressPurposeTypeList purposeTypeList;

    @Inject
    private PartyContactMechanismFacade contactFacade;

    @Override
    public void init(PartyContactMechanism entity) {
        super.init(entity);

        if (entity.getPurposes() != null && !entity.getPurposes().isEmpty()) {
            List<ContactMechanismPurposeType> purposeTypes
                    = contactFacade.findPurposeTypes(entity);
            setPurposes(purposeTypes.stream()
                    .map(pt -> (ElectronicAddressPurposeType) pt)
                    .collect(Collectors.toList()));
        }
    }

    @Override
    public ElectronicAddressPurposeTypeList getPurposeTypeList() {
        return purposeTypeList;
    }

    @Override
    protected Result<String> checkFormComponentValidity() {
        Result<String> result = new Result<>(true, null);
        String addressString = ((ElectronicAddress) entity.getContactMechanism()).getAddressString();
        result.success = (addressString == null || !addressString.isBlank());
        if (!result.success) {
            result.payload = "Alamat elektronik kosong";
        }
        return result;
    }

}
