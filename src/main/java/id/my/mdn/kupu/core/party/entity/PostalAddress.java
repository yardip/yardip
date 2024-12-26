/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.party.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.io.Serializable;
import static java.util.stream.Collectors.joining;
import java.util.stream.Stream;



/**
 *
 * @author aphasan
 */
@Entity
@Table(name = "PARTY_POSTALADDRESS")
public class PostalAddress extends ContactMechanism implements Serializable {

    private static final long serialVersionUID = 1L;
     
    @ManyToOne
    private Country country;
    
     
    @ManyToOne
    private Region region;
    
     
    @ManyToOne
    private District district;    
    
     
    @ManyToOne
    private SubDistrict subDistrict;
    
     
    @ManyToOne
    private Urban urban;
    
     
    @ManyToOne
    private PostalCode postalCode;
    
     
    private String building;
    
     
    private String street;

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    public SubDistrict getSubDistrict() {
        return subDistrict;
    }

    public void setSubDistrict(SubDistrict subDistrict) {
        this.subDistrict = subDistrict;
    }

    public Urban getUrban() {
        return urban;
    }

    public void setUrban(Urban urban) {
        this.urban = urban;
    }

    public PostalCode getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(PostalCode postalCode) {
        this.postalCode = postalCode;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }
    
    public String getValue() {
        Stream<String> stream = Stream.of(
                building,
                street,
                (urban != null ? urban.getName() + ", " : "") +
                (subDistrict != null ? subDistrict.getName() : ""),
                (district != null ? district.getName() : ""),
                (region != null ? "Provinsi " + region.getName() : ""),
                (country != null ? country.getName() + " " : "") +
                (postalCode != null ? postalCode.getCode() : "")
        );
        return stream.filter(str -> str != null).filter(str -> !str.isEmpty())
                .collect(joining("<br/>", "", ""));
    }

}
