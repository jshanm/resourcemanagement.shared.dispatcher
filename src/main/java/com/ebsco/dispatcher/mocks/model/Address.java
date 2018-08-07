package com.ebsco.dispatcher.mocks.model;

import java.io.Serializable;

public interface Address extends Serializable {

    /**
     * Get the system-specific ID of the Address object
     * @return
     */
    public Long getId();

    /**
     * @return the formatted address
     */
    public String getFormatted();

    /**
     * @param formatted the formatted address to set
     */
    public void setFormatted(String formatted);

    /**
     * @return the streetAddress
     */
    public String getStreetAddress();

    /**
     * @param streetAddress the streetAddress to set
     */
    public void setStreetAddress(String streetAddress);

    /**
     * @return the locality
     */
    public String getLocality();

    /**
     * @param locality the locality to set
     */
    public void setLocality(String locality);

    /**
     * @return the region
     */
    public String getRegion();

    /**
     * @param region the region to set
     */
    public void setRegion(String region);

    /**
     * @return the postalCode
     */
    public String getPostalCode();

    /**
     * @param postalCode the postalCode to set
     */
    public void setPostalCode(String postalCode);

    /**
     * @return the country
     */
    public String getCountry();

    /**
     * @param country the country to set
     */
    public void setCountry(String country);

}
