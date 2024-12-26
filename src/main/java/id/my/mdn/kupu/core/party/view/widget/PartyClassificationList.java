/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.party.view.widget;

import id.my.mdn.kupu.core.base.dao.AbstractFacade;
import id.my.mdn.kupu.core.base.view.widget.AbstractMutablePagedValueList;
import id.my.mdn.kupu.core.base.util.FilterTypes.FilterData;
import id.my.mdn.kupu.core.party.dao.PartyClassificationFacade;
import id.my.mdn.kupu.core.party.entity.Party;
import id.my.mdn.kupu.core.party.entity.PartyClassification;
import id.my.mdn.kupu.core.party.entity.PartyType;
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
@Named(value = "partyClassificationList")
@Dependent
public class PartyClassificationList 
        extends AbstractMutablePagedValueList<PartyClassification> {
   
    @Inject
    private PartyClassificationFacade dao;
    
     
    private Party party;

    @PostConstruct
    public void init() {
        filter.staticFilter = this::getPartyFilter;
    }

    private List<FilterData> getPartyFilter() {
        List<FilterData> hiddenFilters = new ArrayList<>();
        hiddenFilters.add(new FilterData("party", party));
        return hiddenFilters;
    }

    @Override
    protected List<PartyClassification> getPagedFetchedItemsInternal(int first, int pageSize, Map<String, Object> parameters, List<FilterData> filters, List<SorterData> sorters, DefaultList<PartyClassification> defaultList, AbstractFacade.DefaultChecker defaultChecker) {
        return dao.findAll(first, pageSize, parameters, filters, sorters);
    }

    @Override
    protected long getItemsCountInternal(Map<String, Object> parameters, List<FilterData> filters, DefaultCount defaultCount, AbstractFacade.DefaultChecker defaultChecker) {
        return dao.countAll(parameters, filters);
    }
    
    public List<PartyClassification> getItems() {
        return dao.findAll(getPartyFilter(), getSorters());
    }

    @Override
    protected void createInternal(PartyClassification entity) {
        dao.create(entity);
    }

    @Override
    public void edit(PartyClassification entity) {
        dao.edit(entity);
    }

    @Override
    protected void deleteInternal(PartyClassification entity) {
        dao.remove(entity);
    }

    public void addClassifications(List<PartyType> types) {
        dao.addClassifications(party, types);
    }

    public Party getParty() {
        return party;
    }

    public void setParty(Party party) {
        this.party = party;
    }
    
}
