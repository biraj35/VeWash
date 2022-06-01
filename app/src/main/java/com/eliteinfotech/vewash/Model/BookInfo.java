package com.eliteinfotech.vewash.Model;

public class BookInfo {
    public String bookID;
    public String userID;
    public String userName;
    public String vehicle; //1 for bike && 2 for car
    public String serviceID;
    public String serviceName;
    public String serviceAmt;
    public String serviceProviderID;
    public String serviceProviderName;
    public String serviceStatus;
    public String serviceDate;
    public String serviceTime;
    public String bookStatus;
    public String userContact;
    public String serviceContact;
    public String createdDate;

    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public BookInfo() {
    }

    public BookInfo(String bookID, String userID, String userName, String vehicle, String serviceID, String serviceName, String serviceAmt, String serviceProviderID, String serviceProviderName, String serviceStatus, String serviceDate, String serviceTime, String bookStatus, String createdDate) {
        this.bookID = bookID;
        this.userID = userID;
        this.userName = userName;
        this.vehicle = vehicle;
        this.serviceID = serviceID;
        this.serviceName = serviceName;
        this.serviceAmt = serviceAmt;
        this.serviceProviderID = serviceProviderID;
        this.serviceProviderName = serviceProviderName;
        this.serviceStatus = serviceStatus;
        this.serviceDate = serviceDate;
        this.serviceTime = serviceTime;
        this.bookStatus = bookStatus;
        this.createdDate = createdDate;
    }

    public String getBookID() {
        return bookID;
    }

    public void setBookID(String bookID) {
        this.bookID = bookID;
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

    public String getServiceID() {
        return serviceID;
    }

    public void setServiceID(String serviceID) {
        this.serviceID = serviceID;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceAmt() {
        return serviceAmt;
    }

    public void setServiceAmt(String serviceAmt) {
        this.serviceAmt = serviceAmt;
    }

    public String getServiceProviderID() {
        return serviceProviderID;
    }

    public void setServiceProviderID(String serviceProviderID) {
        this.serviceProviderID = serviceProviderID;
    }

    public String getServiceProviderName() {
        return serviceProviderName;
    }

    public void setServiceProviderName(String serviceProviderName) {
        this.serviceProviderName = serviceProviderName;
    }

    public String getServiceStatus() {
        return serviceStatus;
    }

    public void setServiceStatus(String serviceStatus) {
        this.serviceStatus = serviceStatus;
    }

    public String getServiceDate() {
        return serviceDate;
    }

    public void setServiceDate(String serviceDate) {
        this.serviceDate = serviceDate;
    }

    public String getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(String serviceTime) {
        this.serviceTime = serviceTime;
    }

    public String getBookStatus() {
        return bookStatus;
    }

    public void setBookStatus(String bookStatus) {
        this.bookStatus = bookStatus;
    }

}
