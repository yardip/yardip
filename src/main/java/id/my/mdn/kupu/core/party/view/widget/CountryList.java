/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.party.view.widget;

import id.my.mdn.kupu.core.base.view.widget.IValueList;
import id.my.mdn.kupu.core.party.dao.CountryFacade;
import id.my.mdn.kupu.core.party.entity.Country;
import java.util.List;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 *
 * @author aphasan
 */
@Named(value = "countryList")
@Dependent
public class CountryList implements IValueList<Country> {
    
    @Inject
    private CountryFacade countryFacade;

    @Override
    public List<Country> getFetchedItems() {
        List<Country> ret = countryFacade.findAll();
        return ret;
    }
    
    public Country findByName(String name) {
        return countryFacade.findByName(name, null);
    }
    
}
