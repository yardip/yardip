/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.party.view;

import id.my.mdn.kupu.core.base.view.MultipleChooserPage;
import id.my.mdn.kupu.core.base.view.annotation.Bookmarked;
import id.my.mdn.kupu.core.base.view.converter.SelectionsConverter;
import id.my.mdn.kupu.core.party.entity.PersonType;
import id.my.mdn.kupu.core.party.view.converter.PersonTypeListConverter;
import id.my.mdn.kupu.core.party.view.widget.PersonTypeList;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author aphasan
 */
@Named(value = "personTypeChooserPage")
@ViewScoped
public class PersonTypeChooserPage extends MultipleChooserPage<PersonType> implements Serializable {
    
    @Inject
    @Bookmarked
    private PersonTypeList typeList;

    @Override
    protected List<PersonType> returns() {
        return typeList.getSelections();
    }

    @Override
    protected Class<? extends SelectionsConverter<PersonType>> getConverter() {
        return PersonTypeListConverter.class;
    }

    public PersonTypeList getTypeList() {
        return typeList;
    }
    
}
