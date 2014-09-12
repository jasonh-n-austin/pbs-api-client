package com.paperbackswap.data;

public class MemberDataImpl implements MemberData {
    private String name;
    private String careOf;
    private String streetAddress;
    private String city;
    private String state;
    private String zipCode;
    private Integer credits;
    private String pbsMoney;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getCareOf() {
        return careOf;
    }

    @Override
    public void setCareOf(String careOf) {
        this.careOf = careOf;
    }

    @Override
    public String getStreetAddress() {
        return streetAddress;
    }

    @Override
    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    @Override
    public String getCity() {
        return city;
    }

    @Override
    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String getState() {
        return state;
    }

    @Override
    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String getZipCode() {
        return zipCode;
    }

    @Override
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    @Override
    public Integer getCredits() {
        return credits;
    }

    @Override
    public void setCredits(Integer credits) {
        this.credits = credits;
    }

    @Override
    public String getPbsMoney() {
        return pbsMoney;
    }

    @Override
    public void setPbsMoney(String money) {
        this.pbsMoney = money;
    }
}
