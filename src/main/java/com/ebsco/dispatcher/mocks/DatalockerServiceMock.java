package com.ebsco.dispatcher.mocks;

import com.ebsco.dispatcher.mocks.model.PUAUserInfo;
import com.ebsco.dispatcher.service.DatalockerService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DatalockerServiceMock implements DatalockerService {

    private final static String USERNAME = "testuser";
    private final static String PASSWORD = "testpass";

    public Optional<PUAUserInfo> getUserInfo(String datalockerKey) {

        final List<String> roles = new ArrayList<String>();
        roles.add("ADMIN");
        final List<String> affiliations = new ArrayList<String>();
        affiliations.add("cust1.group1");
        return Optional.of(new PUAUserInfo("1234", USERNAME + "@example.com", roles, affiliations, "test_fname", "test_lname", USERNAME, "true", "false", Optional.empty(), Optional.empty(), Optional.empty()));

    }
}
