package com.ebsco.dispatcher.mocks.model;

import java.io.Serializable;

public class DatalockerValues implements Serializable {
   /* Logon: 1
    ProfID: ehost
    CustID: noble
    GroupID: main
    ProfPwd: xxxxx (Profile password has 5 digits and is NOT displayed for security reason)
    Lang: en
    AuthMethod: uid
    LP:
    RefURL: http://auth-edc.ebscohost.com/login.aspx?debug=true
    defaultView: R
    PatronID:
    SiteUrl: http://ebscohost.ads.ade.epnet.com/start
    Named User:
    UniqueUserId: -21912191
    MachineId: 5392a632-be0e-4825-a9e9-98fffc44a5f1
    IsAdminMobile: N
    HardwareModel: Macintosh
    HardwareName: Macintosh
    HardwareVendor: Apple
    IsMobile: False
    IsTablet: False
    ScreenMMHeight: Unknown
    ScreenMMWidth: Unknown
    PixelsScreenHeight: Unknown
    PixelsScreenWidth: Unknown
    PlatformName: macOS
    PlatformVendor: Apple
    PlatformVersion: 10.13.6
    BrowserName: Chrome
    BrowserVendor: Google
    BrowserVersion: 68
    FirstName:
    LastName:
    Email:
    ServiceAuthentication:
    SsoUniqueId:
    encId: 22D731263C1635973716352632153C97391373C376C371C376C371C371C376C33013
    InstitutionId:
    SPNameQualifier:
    crlhashurl: Community.aspx%3fdebug%3dtrue%26authtype%3duid%26encid%3d22D731263C1635973716352632153C97391373C376C371C376C371C371C376C33013%26IsAdminMobile%3dN%26authpid%3dehost
    TrialUserAffiliatedToCust: False
    Client IP: 10.254.6.81*/

   String profileId;
   String custId;
   String groupId;
   String profileLanguage;
   String authMethod;
   String refUrl;
   String siteUrl;
   String namedUser;
   String uniqueUserId;
   String machineId;
   String firstName;
   String lastName;
   String email;
   String servieAuthentication;
   String ssoUniqueId;
   String institutionId;
   String clientIp;

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getProfileLanguage() {
        return profileLanguage;
    }

    public void setProfileLanguage(String profileLanguage) {
        this.profileLanguage = profileLanguage;
    }

    public String getAuthMethod() {
        return authMethod;
    }

    public void setAuthMethod(String authMethod) {
        this.authMethod = authMethod;
    }

    public String getRefUrl() {
        return refUrl;
    }

    public void setRefUrl(String refUrl) {
        this.refUrl = refUrl;
    }

    public String getSiteUrl() {
        return siteUrl;
    }

    public void setSiteUrl(String siteUrl) {
        this.siteUrl = siteUrl;
    }

    public String getNamedUser() {
        return namedUser;
    }

    public void setNamedUser(String namedUser) {
        this.namedUser = namedUser;
    }

    public String getUniqueUserId() {
        return uniqueUserId;
    }

    public void setUniqueUserId(String uniqueUserId) {
        this.uniqueUserId = uniqueUserId;
    }

    public String getMachineId() {
        return machineId;
    }

    public void setMachineId(String machineId) {
        this.machineId = machineId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getServieAuthentication() {
        return servieAuthentication;
    }

    public void setServieAuthentication(String servieAuthentication) {
        this.servieAuthentication = servieAuthentication;
    }

    public String getSsoUniqueId() {
        return ssoUniqueId;
    }

    public void setSsoUniqueId(String ssoUniqueId) {
        this.ssoUniqueId = ssoUniqueId;
    }

    public String getInstitutionId() {
        return institutionId;
    }

    public void setInstitutionId(String institutionId) {
        this.institutionId = institutionId;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }
}
