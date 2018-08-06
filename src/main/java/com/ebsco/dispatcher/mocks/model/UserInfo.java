package com.ebsco.dispatcher.mocks.model;

import com.google.gson.JsonObject;

public interface UserInfo {
    /**
     * @return the userId
     */
    public String getSub();

    /**
     * @param sub the userId to set
     */
    public void setSub(String sub);

    /**
     * @return the preferred username
     */
    public String getPreferredUsername();

    /**
     * @param preferredUsername the preferredUsername to set
     */
    public void setPreferredUsername(String preferredUsername);

    /**
     * @return the name
     */
    public String getName();

    /**
     * @param name the name to set
     */
    public void setName(String name);

    /**
     * @return the givenName
     */
    public String getGivenName();

    /**
     * @param givenName the givenName to set
     */
    public void setGivenName(String givenName);

    /**
     * @return the familyName
     */
    public String getFamilyName();

    /**
     * @param familyName the familyName to set
     */
    public void setFamilyName(String familyName);

    /**
     * @return the middleName
     */
    public String getMiddleName();

    /**
     * @param middleName the middleName to set
     */
    public void setMiddleName(String middleName);

    /**
     * @return the nickname
     */
    public String getNickname();

    /**
     * @param nickname the nickname to set
     */
    public void setNickname(String nickname);

    /**
     * @return the profile
     */
    public String getProfile();

    /**
     * @param profile the profile to set
     */
    public void setProfile(String profile);

    /**
     * @return the picture
     */
    public String getPicture();

    /**
     * @param picture the picture to set
     */
    public void setPicture(String picture);

    /**
     * @return the website
     */
    public String getWebsite();

    /**
     * @param website the website to set
     */
    public void setWebsite(String website);

    /**
     * @return the email
     */
    public String getEmail();

    /**
     * @param email the email to set
     */
    public void setEmail(String email);

    /**
     * @return the verified
     */
    public Boolean getEmailVerified();

    /**
     *
     */
    public void setEmailVerified(Boolean emailVerified);

    /**
     * @return the gender
     */
    public String getGender();

    /**
     * @param gender the gender to set
     */
    public void setGender(String gender);

    /**
     * @return the zoneinfo
     */
    public String getZoneinfo();

    /**
     * @param zoneinfo the zoneinfo to set
     */
    public void setZoneinfo(String zoneinfo);

    /**
     * @return the locale
     */
    public String getLocale();

    /**
     * @param locale the locale to set
     */
    public void setLocale(String locale);

    /**
     * @return the phoneNumber
     */
    public String getPhoneNumber();

    /**
     * @param phoneNumber the phoneNumber to set
     */
    public void setPhoneNumber(String phoneNumber);

    /**
     *
     */
    public Boolean getPhoneNumberVerified();

    /**
     *
     * @param phoneNumberVerified
     */
    public void setPhoneNumberVerified(Boolean phoneNumberVerified);

    /**
     * @return the address
     */
    public Address getAddress();

    /**
     * @param address the address to set
     */
    public void setAddress(Address address);

    /**
     * @return the updatedTime
     */
    public String getUpdatedTime();

    /**
     * @param updatedTime the updatedTime to set
     */
    public void setUpdatedTime(String updatedTime);


    /**
     *
     * @return
     */
    public String getBirthdate();

    /**
     *
     * @param birthdate
     */
    public void setBirthdate(String birthdate);

    /**
     * Serialize this UserInfo object to JSON.
     *
     * @return
     */
    public JsonObject toJson();

    /**
     * The JSON source of this UserInfo (if it was fetched), or null if it's local.
     * @return
     */
    public JsonObject getSource();

}
