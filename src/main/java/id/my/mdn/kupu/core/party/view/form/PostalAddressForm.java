/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.party.view.form;

import id.my.mdn.kupu.core.base.util.Result;
import id.my.mdn.kupu.core.party.dao.CountryFacade;
import id.my.mdn.kupu.core.party.dao.DistrictFacade;
import id.my.mdn.kupu.core.party.dao.PartyContactMechanismFacade;
import id.my.mdn.kupu.core.party.dao.RegionFacade;
import id.my.mdn.kupu.core.party.entity.ContactMechanismPurposeType;
import id.my.mdn.kupu.core.party.entity.Country;
import id.my.mdn.kupu.core.party.entity.District;
import id.my.mdn.kupu.core.party.entity.PartyContactMechanism;
import id.my.mdn.kupu.core.party.entity.PostalAddress;
import id.my.mdn.kupu.core.party.entity.PostalAddressPurposeType;
import id.my.mdn.kupu.core.party.entity.PostalCode;
import id.my.mdn.kupu.core.party.entity.Region;
import id.my.mdn.kupu.core.party.entity.SubDistrict;
import id.my.mdn.kupu.core.party.view.widget.CountryList;
import id.my.mdn.kupu.core.party.view.widget.DistrictList;
import id.my.mdn.kupu.core.party.view.widget.PostalAddressPurposeTypeList;
import id.my.mdn.kupu.core.party.view.widget.RegionList;
import id.my.mdn.kupu.core.party.view.widget.SubDistrictList;
import id.my.mdn.kupu.core.party.view.widget.UrbanList;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.configuration2.Configuration;

/**
 *
 * @author aphasan
 */
@Named(value = "postalAddressForm")
@Dependent
public class PostalAddressForm extends PartyContactMechanismForm<PostalAddress, PostalAddressPurposeType> {

    @Inject
    private CountryList countryList;

    @Inject
    private RegionList regionList;

    @Inject
    private DistrictList districtList;

    @Inject
    private SubDistrictList subDistrictList;

    @Inject
    private UrbanList urbanList;

    @Inject
    private Configuration config;

    @Inject
    private CountryFacade countryFacade;

    @Inject
    private RegionFacade regionFacade;

    @Inject
    private DistrictFacade districtFacade;

    @Inject
    private PostalAddressPurposeTypeList purposeTypeList;

    @Inject
    private PartyContactMechanismFacade contactFacade;

    @Override
    public void init(PartyContactMechanism entity) {
        super.init(entity);

        if (entity.getPurposes() != null && !entity.getPurposes().isEmpty()) {
            List<ContactMechanismPurposeType> purposeTypes
                    = contactFacade.findPurposeTypes(entity);
            setPurposes(purposeTypes.stream()
                    .map(pt -> (PostalAddressPurposeType) pt)
                    .collect(Collectors.toList()));
        }

        PostalAddress contact = (PostalAddress) entity.getContactMechanism();

        Country country = contact.getCountry();
        if (country == null) {
            String countryName = config.getString("application.Country");
            country = countryFacade
                    .findSingleByAttribute("name", countryName);
        }
        getContactMechanism().setCountry(country);

        regionList.setParent(country);

        Region region = contact.getRegion();
        if (region == null) {
            String regionName = config.getString("application.Region");
            region = regionFacade
                    .findSingleByAttribute("name", regionName);
        }
        getContactMechanism().setRegion(region);

        districtList.setParent(region);

        District district = contact.getDistrict();
        if (district == null) {
            String districtName = config.getString("application.District");
            district = districtFacade
                    .findSingleByAttribute("name", districtName);
        }
        getContactMechanism().setDistrict(district);

        subDistrictList.setParent(district);

        SubDistrict subDistrict = contact.getSubDistrict();
        getContactMechanism().setSubDistrict(subDistrict);

        urbanList.setParent(subDistrict);

        getContactMechanism().setUrban(contact.getUrban());
        getContactMechanism().setBuilding(contact.getBuilding());
        getContactMechanism().setStreet(contact.getStreet());
        getContactMechanism().setPostalCode(contact.getPostalCode());
    }

    public void onCountryChanged() {
        regionList.setParent(getContactMechanism().getCountry());
        districtList.setParent(null);
        subDistrictList.setParent(null);
        urbanList.setParent(null);
        getContactMechanism().setPostalCode(null);
    }

    public void onRegionChanged() {
        districtList.setParent(getContactMechanism().getRegion());
        subDistrictList.setParent(null);
        urbanList.setParent(null);
        getContactMechanism().setPostalCode(getContactMechanism().getRegion().getPostalCode());
    }

    public void onDistrictChanged() {
        subDistrictList.setParent(getContactMechanism().getDistrict());
        urbanList.setParent(null);
        getContactMechanism().setPostalCode(getContactMechanism().getDistrict().getPostalCode());
    }

    public void onSubDistrictChanged() {
        urbanList.setParent(getContactMechanism().getSubDistrict());
        getContactMechanism().setPostalCode(getContactMechanism().getSubDistrict().getPostalCode());
    }

    public void onUrbanChanged() {
        getContactMechanism().setPostalCode(getContactMechanism().getUrban().getPostalCode());
    }

    public String getZip() {
        PostalAddress postalAddress = (PostalAddress) entity.getContactMechanism();
        PostalCode postalCode = postalAddress.getPostalCode();
        return postalCode != null ? postalCode.getCode() : null;
    }

    @Override
    public PostalAddressPurposeTypeList getPurposeTypeList() {
        return purposeTypeList;
    }

    public CountryList getCountryList() {
        return countryList;
    }

    public RegionList getRegionList() {
        return regionList;
    }

    public DistrictList getDistrictList() {
        return districtList;
    }

    public SubDistrictList getSubDistrictList() {
        return subDistrictList;
    }

    public UrbanList getUrbanList() {
        return urbanList;
    }

    @Override
    protected Result<String> checkFormComponentValidity() {
        Result<String> result = new Result<>(true, null);
        String street = ((PostalAddress) entity.getContactMechanism()).getStreet();
        result.success = (street == null || !street.isBlank());
        if (!result.success) {
            result.payload = "Alamat kosong";
        }
        return result;
    }

}
