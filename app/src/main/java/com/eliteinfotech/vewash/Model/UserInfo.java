package com.eliteinfotech.vewash.Model;

public class UserInfo {
    public String userID;
    public String userName;
    public String password;
    public String fullName;
    public String userContact;
    public String userType;
    public String notToken;
    public String isVerfied;
    public String orgName;
    public String address;

    public String getUserContact() {
        return userContact;
    }

    public void setUserContact(String userContact) {
        this.userContact = userContact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String userLat;
    public String userLong;

    public UserInfo() {
    }


    public UserInfo(String userID, String userName, String password, String fullName, String userContact, String userType, String notToken, String isVerfied, String orgName, String address, String userLat, String userLong) {
        this.userID = userID;
        this.userName = userName;
        this.password = password;
        this.fullName = fullName;
        this.userContact = userContact;
        this.userType = userType;
        this.notToken = notToken;
        this.isVerfied = isVerfied;
        this.orgName = orgName;
        this.address = address;
        this.userLat = userLat;
        this.userLong = userLong;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getNotToken() {
        return notToken;
    }

    public void setNotToken(String notToken) {
        this.notToken = notToken;
    }

    public String getIsVerfied() {
        return isVerfied;
    }

    public void setIsVerfied(String isVerfied) {
        this.isVerfied = isVerfied;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getUserLat() {
        return userLat;
    }

    public void setUserLat(String userLat) {
        this.userLat = userLat;
    }

    public String getUserLong() {
        return userLong;
    }

    public void setUserLong(String userLong) {
        this.userLong = userLong;
    }
}
