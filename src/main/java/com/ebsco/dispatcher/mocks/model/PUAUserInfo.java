package com.ebsco.dispatcher.mocks.model;

import java.util.List;
import java.util.Optional;

import com.google.gson.JsonObject;

/**
 * A customized MITREId {@link UserInfo} implementation
 * @author nick
 *
 * TODO: Fill in mappings between PUA service and UserInfo POJO
 */
public class PUAUserInfo implements UserInfo {

    private String puaid;
    private String puaType;
    private String email;
    private List<String> roles;
    private List<String> affiliations;
    private String givenname;
    private String familyname;
    private String sub;
    private Boolean isEmailUnique;
    private Boolean isEmailVerified;
    private Optional<String> specialty = Optional.empty();
    private Optional<String> userRole = Optional.empty();
    private Optional<String> institution = Optional.empty();

    public PUAUserInfo(final String puaid, String email, final List<String> roles, final List<String> affiliations, String sub){
        this.puaid = puaid;
        this.email = email;
        this.roles = roles;
        this.givenname = "testfirstname";
        this.familyname = "testlastname";
        this.sub = sub;
        this.affiliations = affiliations;
        this.isEmailUnique= null;
        this.isEmailVerified = null;

    }

    public PUAUserInfo(final String puaid, String email, final List<String> roles, final List<String> affiliations,
                       final String givenname, final String familyname, final String sub, String isEmailUnique,  String isEmailVerified,
                       Optional<String> specialty, Optional<String> userRole, Optional<String> institution){

        this(puaid, email, roles, affiliations,
                givenname, familyname, sub, isEmailUnique,  isEmailVerified,
                specialty, userRole, institution,
                null);
    }


    public PUAUserInfo(final String puaid, String email, final List<String> roles, final List<String> affiliations,
                       final String givenname, final String familyname, final String sub, String isEmailUnique,  String isEmailVerified,
                       Optional<String> specialty, Optional<String> userRole, Optional<String> institution,
                       final String puatype){
        this.puaid = puaid;
        this.email = email;
        this.roles = roles;
        this.givenname = givenname;
        this.familyname = familyname;
        this.sub = sub;
        this.affiliations = affiliations;
        this.isEmailUnique= new Boolean(isEmailUnique);
        this.isEmailVerified = new Boolean(isEmailVerified);
        this.puaType = puatype;

        if(specialty.isPresent()) {
            this.specialty = specialty;
        }
        if(userRole.isPresent()) {
            this.userRole = userRole;
        }
        if(institution.isPresent()) {
            this.institution = institution;
        }
    }


    @Override public String getSub() {
        return this.sub;
    }

    @Override public void setSub(String sub) {
        this.sub = sub;
    }

    @Override public String getPreferredUsername() {
        return null;
    }

    @Override public void setPreferredUsername(String preferredUsername) {
        //TODO
    }

    public String getPuaid() {
        return this.puaid;
    }

    public void setPuaid(String puaid) {
        this.puaid =puaid;
    }

    @Override public String getGivenName() {
        return this.givenname;
    }

    @Override public void setGivenName(String givenName) {
        this.givenname = givenName;
    }

    @Override public String getFamilyName() {
        return this.familyname;
    }

    @Override public void setFamilyName(String familyName) {
        this.familyname = familyName;
    }

    @Override public String getMiddleName() {
        return null;
    }

    @Override public void setMiddleName(String middleName) {
        //TODO
    }

    @Override public String getNickname() {
        return null;
    }

    @Override public void setNickname(String nickname) {
        //TODO
    }

    @Override public String getProfile() {
        return null;
    }

    @Override public void setProfile(String profile) {
        //TODO
    }

    @Override public String getPicture() {
        return null;
    }

    @Override public void setPicture(String picture) {

    }

    @Override public String getWebsite() {
        return null;
    }

    @Override public void setWebsite(String website) {

    }

    @Override public String getEmail() {
        return this.email;
    }

    @Override public void setEmail(String email) {

    }

    @Override public Boolean getEmailVerified() {
        return this.isEmailVerified;
    }

    @Override public void setEmailVerified(Boolean emailVerified) {

    }

    @Override public String getGender() {
        return null;
    }

    @Override public void setGender(String gender) {

    }

    @Override public String getZoneinfo() {
        return null;
    }

    @Override public void setZoneinfo(String zoneinfo) {

    }

    @Override public String getLocale() {
        return null;
    }

    @Override public void setLocale(String locale) {

    }

    @Override public String getPhoneNumber() {
        return null;
    }

    @Override public void setPhoneNumber(String phoneNumber) {

    }

    @Override public Boolean getPhoneNumberVerified() {
        return null;
    }

    @Override public void setPhoneNumberVerified(Boolean phoneNumberVerified) {

    }

    @Override public Address getAddress() {
        return null;
    }

    @Override public void setAddress(Address address) {

    }

    @Override public String getUpdatedTime() {
        return null;
    }

    @Override public void setUpdatedTime(String updatedTime) {

    }

    @Override public String getBirthdate() {
        return null;
    }

    @Override public void setBirthdate(String birthdate) {

    }

    @Override public JsonObject toJson() {
        return null;
    }

    @Override public JsonObject getSource() {
        return null;
    }


    public List<String> getRoles(){
        return this.roles;
    }

    public List<String> getAffiliations(){
        return this.affiliations;
    }

    @Override
    public String getName() {
        // this is username
        return sub;
    }

    @Override
    public void setName(String name) {
        // this will set the sub as username
        this.sub = name;
    }

    public Boolean getEmailUnique() {
        return isEmailUnique;
    }

    public void setEmailUnique(Boolean is_email_unique) {
        this.isEmailUnique = is_email_unique;
    }

    public Optional<String> getSpecialty() {
        return this.specialty;
    }

    public Optional<String> getUserRole() {
        return this.userRole;
    }

    public Optional<String> getInstitution() {
        return this.institution;
    }


    public String getPuaType() {
        return this.puaType;
    }

}

