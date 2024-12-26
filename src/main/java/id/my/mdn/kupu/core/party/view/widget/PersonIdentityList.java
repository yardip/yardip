/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.party.view.widget;

import id.my.mdn.kupu.core.base.dao.AbstractFacade;
import id.my.mdn.kupu.core.base.view.widget.AbstractMutablePagedValueList;
import id.my.mdn.kupu.core.base.util.FilterTypes.FilterData;
import id.my.mdn.kupu.core.party.dao.PersonIdentityFacade;
import id.my.mdn.kupu.core.party.entity.Person;
import id.my.mdn.kupu.core.party.entity.PersonIdentity;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 *
 * @author aphasan
 */
@Named(value = "personIdentityList")
@Dependent
public class PersonIdentityList 
        extends AbstractMutablePagedValueList<PersonIdentity> {

    @Inject
    private PersonIdentityFacade dao;    
     
    private Person person;

    @PostConstruct
    public void init() {
        filter.staticFilter = this::getStaticFilter;
    }

    private List<FilterData> getStaticFilter() {
        List<FilterData> filters = new ArrayList<>();
        filters.add(new FilterData("person", person));
        return filters;
    }

    @Override
    protected List<PersonIdentity> getPagedFetchedItemsInternal(int first, int pageSize, Map<String, Object> parameters, List<FilterData> filters, List<SorterData> sorters, DefaultList<PersonIdentity> defaultList, AbstractFacade.DefaultChecker defaultChecker) {
        return dao.findAll(first, pageSize, parameters, filters, sorters);
    }

    @Override
    protected long getItemsCountInternal(Map<String, Object> parameters, List<FilterData> filters, DefaultCount defaultCount, AbstractFacade.DefaultChecker defaultChecker) {
        return dao.countAll(parameters, filters);
    }

    @Override
    protected void createInternal(PersonIdentity entity) {
        dao.create(entity);
    }

    @Override
    public void edit(PersonIdentity entity) {
        dao.edit(entity);
    }

    @Override
    protected void deleteInternal(PersonIdentity entity) {
        dao.remove(entity);
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
    
}
