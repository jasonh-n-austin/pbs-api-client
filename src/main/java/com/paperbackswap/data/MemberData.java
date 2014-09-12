package com.paperbackswap.data;

public interface MemberData {
    public String getName();
    public void setName(String name);
    public String getCareOf();
    public void setCareOf(String careOf);
    public String getStreetAddress();
    public void setStreetAddress(String streetAddress);
    public String getCity();
    public void setCity(String city);
    public String getState();
    public void setState(String state);
    public String getZipCode();
    public void setZipCode(String zipCode);
    public Integer getCredits();
    public void setCredits(Integer credits);
    public String getPbsMoney();
    public void setPbsMoney(String money);
}
