/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.party.view;

import id.my.mdn.kupu.core.base.util.FilterTypes.FilterData;
import id.my.mdn.kupu.core.base.view.MultipleChooserPage;
import id.my.mdn.kupu.core.base.view.annotation.Bookmarked;
import id.my.mdn.kupu.core.base.view.annotation.OnInit;
import id.my.mdn.kupu.core.base.view.converter.SelectionsConverter;
import id.my.mdn.kupu.core.party.entity.Party;
import id.my.mdn.kupu.core.party.entity.PartyRoleType;
import id.my.mdn.kupu.core.party.view.converter.PartyRoleTypeListConverter;
import id.my.mdn.kupu.core.party.view.widget.PartyRoleTypeList;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author aphasan
 */
@Named(value = "partyRoleTypeChooserPage")
@ViewScoped
public class PartyRoleTypeChooserPage extends MultipleChooserPage<PartyRoleType> implements Serializable {
    
    @Inject
    @Bookmarked
    private PartyRoleTypeList typeList;
    
    @Bookmarked
    private String partyType;
    
    @Bookmarked
    private Party party;

    public Party getParty() {
        return party;
    }

    public void setParty(Party party) {
        this.party = party;
    }

    @OnInit
    public void init() {
        typeList.getFilter().setStaticFilter(this::staticFilter);
    }
    
    private List<FilterData> staticFilter() {
        List<FilterData> staticFilter = new ArrayList<>();
        staticFilter.add(new FilterData("partyType", partyType + "RoleType"));
        staticFilter.add(new FilterData("excluded", party.getRoles().stream()
                .map(role -> role.getPartyRoleType())
                .map(roleType -> roleType.getName())
                .collect(Collectors.toList()))
        );
        return staticFilter;
    }

    @Override
    protected List<PartyRoleType> returns() {
        return typeList.getSelections();
    }

    @Override
    protected Class<? extends SelectionsConverter<PartyRoleType>> getConverter() {
        return PartyRoleTypeListConverter.class;
    }

    public PartyRoleTypeList getTypeList() {
        return typeList;
    }

    public String getPartyType() {
        return partyType;
    }

    public void setPartyType(String partyType) {
        this.partyType = partyType;
    }
    
}
