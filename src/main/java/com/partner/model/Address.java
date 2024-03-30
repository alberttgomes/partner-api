package com.partner.model;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
public class Address implements Serializable {
    public Long getAddressId() {
        return _addressId;
    }

    public void setAddressId(Long addressId) {
        this._addressId = addressId;
    }

    public String getCity() {
        return _city;
    }

    public void setCity(String city) {
        this._city = city;
    }

    public String getCountry() {
        return _country;
    }

    public void setCountry(String country) {
        this._country = country;
    }

    public String getDistrict() {
        return _district;
    }

    public void setDistrict(String district) {
        this._district = district;
    }

    public Partnership getPartnership() {
        return _partnership;
    }

    public void setPartnership(Partnership partnership) {
        this._partnership = partnership;
    }

    public int getNumber() {
        return _number;
    }

    public void setNumber(int number) {
        this._number = number;
    }

    public String getStreet() {
        return _street;
    }

    public void setStreet(String street) {
        this._street = street;
    }

    @Override
    public String toString() {
        return "Street: " + _street + "\n Number: " + _number + "\n District: " +
                _district + "\n " + _country + " - " + _city;
    }

    @ManyToOne(targetEntity = Partnership.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "_partnershipId")
    private Partnership _partnership;

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long _addressId;
    @Column(nullable = false, length = 50)
    private String _city;
    @Column(nullable = false, length = 30)
    private String _country;
    @Column(nullable = false, length = 40)
    private String _district;
    @Column(nullable = false)
    private int _number;
    @Column(nullable = false, length = 50)
    private String _street;
}
