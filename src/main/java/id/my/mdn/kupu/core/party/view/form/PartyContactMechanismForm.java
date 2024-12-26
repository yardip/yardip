/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.party.view.form;

import id.my.mdn.kupu.core.base.view.FormComponent;
import id.my.mdn.kupu.core.base.view.widget.IValueList;
import id.my.mdn.kupu.core.party.entity.ContactMechanism;
import id.my.mdn.kupu.core.party.entity.ContactMechanismPurposeType;
import id.my.mdn.kupu.core.party.entity.PartyContactMechanism;
import id.my.mdn.kupu.core.party.entity.PartyContactMechanismPurpose;
import id.my.mdn.kupu.core.party.entity.PartyContactMechanismPurposeId;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author aphasan
 * @param <T>
 * @param <P>
 */
public abstract class PartyContactMechanismForm<T extends ContactMechanism, P extends ContactMechanismPurposeType> extends FormComponent<PartyContactMechanism> {

    public T getContactMechanism() {
        return (T) entity.getContactMechanism();
    }
    
    private List<P> purposes = new ArrayList<>();

    @Override
    protected void doPack() {
        purposes.stream().forEach(this::toPurpose);
    }

    private void toPurpose(P purposeType) {
        PartyContactMechanismPurpose purpose = new PartyContactMechanismPurpose();
        purpose.setId(new PartyContactMechanismPurposeId());
        purpose.setPartyContactMechanism(entity);
        purpose.setPurposeType(purposeType);
        entity.getPurposes().add(purpose);
    }

    public List<P> getPurposes() {
        return purposes;
    }

    public void setPurposes(List<P> purposes) {
        this.purposes = purposes;
    }

    public abstract <R extends IValueList<P>> R getPurposeTypeList();
}
