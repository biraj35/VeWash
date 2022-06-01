package com.eliteinfotech.vewash.Model;

public class ServiceProviderInfo {
    public String spID,spName,spLat,spLong,spAddress,spContact;

    public ServiceProviderInfo(String spID, String spName, String spLat, String spLong, String spAddress, String spContact) {
        this.spID = spID;
        this.spName = spName;
        this.spLat = spLat;
        this.spLong = spLong;
        this.spAddress = spAddress;
        this.spContact = spContact;
    }

    public String getSpID() {
        return spID;
    }

    public void setSpID(String spID) {
        this.spID = spID;
    }

    public String getSpName() {
        return spName;
    }

    public void setSpName(String spName) {
        this.spName = spName;
    }

    public String getSpLat() {
        return spLat;
    }

    public void setSpLat(String spLat) {
        this.spLat = spLat;
    }

    public String getSpLong() {
        return spLong;
    }

    public void setSpLong(String spLong) {
        this.spLong = spLong;
    }

    public String getSpAddress() {
        return spAddress;
    }

    public void setSpAddress(String spAddress) {
        this.spAddress = spAddress;
    }

    public String getSpContact() {
        return spContact;
    }

    public void setSpContact(String spContact) {
        this.spContact = spContact;
    }
}
