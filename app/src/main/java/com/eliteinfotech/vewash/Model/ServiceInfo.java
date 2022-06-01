package com.eliteinfotech.vewash.Model;

public class ServiceInfo {
    public String serviceID,serviceName,serviceAmt,spID,spName,spOrgName;

    public ServiceInfo(String serviceID, String serviceName, String serviceAmt, String spID, String spName, String spOrgName) {
        this.serviceID = serviceID;
        this.serviceName = serviceName;
        this.serviceAmt = serviceAmt;
        this.spID = spID;
        this.spName = spName;
        this.spOrgName = spOrgName;
    }

    public ServiceInfo() {
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

    public String getSpOrgName() {
        return spOrgName;
    }

    public void setSpOrgName(String spOrgName) {
        this.spOrgName = spOrgName;
    }
}
