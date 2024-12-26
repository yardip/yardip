/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.party.view;

import id.my.mdn.kupu.core.base.view.MultipleChooserPage;
import id.my.mdn.kupu.core.base.view.annotation.Bookmarked;
import id.my.mdn.kupu.core.base.view.converter.SelectionsConverter;
import id.my.mdn.kupu.core.party.entity.Person;
import id.my.mdn.kupu.core.party.view.converter.PersonListConverter;
import id.my.mdn.kupu.core.party.view.widget.PersonList;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Medina Computama <medina.computama@gmail.com>
 */
@Named(value = "personChooserPage")
@ViewScoped
public class PersonChooserPage extends MultipleChooserPage<Person> implements Serializable {
    
    @Inject
    @Bookmarked
    private PersonList dataView;

    @Override
    protected List<Person> returns() {
        return dataView.getSelections();
    }

    public PersonList getDataView() {
        return dataView;
    }

    @Override
    protected Class<? extends SelectionsConverter<Person>> getConverter() {
        return PersonListConverter.class;
    }

}
