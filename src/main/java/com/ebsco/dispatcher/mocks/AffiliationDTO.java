package com.ebsco.dispatcher.mocks;


import static com.google.common.base.Preconditions.checkArgument;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * AffiliationDTO contains a valid LookupMethod, customer ID
 * and group ID representing the affiliation for an institutional context
 *
 * The LookupMethod is NOT validated by this class.  It is the responsibility
 * of the calling application to pass something meaningful.
 *
 * Sample JSON for response object containing the affiliation info for
 * institutional authentication/lookup { "lookupMethod":"ip",
 * "custId":"s123456", "groupId":"main" }
 */
@ApiModel(description = "AffiliationDTO contains a valid LookupMethod (enumerated value), customer ID and group ID.")
public class AffiliationDTO {

    protected static final String LOOKUP_METHOD_ERROR_MESSAGE = "LookupMethod cannot be null or empty";
    protected static final String CUSTID_ERROR_MESSAGE = "CustId cannot be null or empty";
    protected static final String GROUPID_ERROR_MESSAGE = "GroupId cannot be null or empty";

    /**
     * The LookupMethod indicates what authentication method was used to lookup
     * the affiliation
     */
    @ApiModelProperty(required = true, value = "lookupMethod", example = "IP, REFURL")
    private final String lookupMethod;

    /**
     * The customer id of the affiliation
     */
    @ApiModelProperty(required = true, value = "custId", example = "demo")
    private final String custId;

    /**
     * The group id of the affiliation
     */
    @ApiModelProperty(required = true, value = "groupId", example = "main")
    private final String groupId;

    @JsonCreator
    public AffiliationDTO(@JsonProperty("lookupMethod") String lookupMethod,
                          @JsonProperty("custId") String custId, @JsonProperty("groupId") String groupId) {
        // check the arguments
        checkArgument(StringUtils.isNotEmpty(lookupMethod), LOOKUP_METHOD_ERROR_MESSAGE);
        checkArgument(StringUtils.isNotEmpty(custId), CUSTID_ERROR_MESSAGE);
        checkArgument(StringUtils.isNotEmpty(groupId), GROUPID_ERROR_MESSAGE);

        // set the member variables
        this.lookupMethod = lookupMethod;
        this.custId = custId;
        this.groupId = groupId;
    }

    public String getLookupMethod() {
        return lookupMethod;
    }

    public String getCustId() {
        return custId;
    }

    public String getGroupId() {
        return groupId;
    }

    @Override
    public boolean equals(Object obj) {
        if (null == obj || !(obj instanceof AffiliationDTO)) {
            return false;
        }

        AffiliationDTO that = (AffiliationDTO) obj;
        return Objects.equal(this.getLookupMethod(), that.getLookupMethod())
                && Objects.equal(this.getCustId(), that.getCustId())
                && Objects.equal(this.getGroupId(), that.getGroupId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.getLookupMethod(), this.getCustId(), this.getGroupId());
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("lookup method", this.getLookupMethod())
                .add("custId", this.getCustId()).add("groupId", this.getGroupId()).toString();
    }
}
