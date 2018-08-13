package com.ebsco.dispatcher.service;

import com.ebsco.dispatcher.mocks.model.DatalockerValues;
import com.ebsco.dispatcher.mocks.model.PUAUserInfo;
import com.ebsco.dispatcher.model.UserInformation;

import java.util.Optional;

public interface DatalockerService {

    //public UserInformation getUserInformation(String datalockeKey);

    public Optional<PUAUserInfo> getUserInfo(String datalockerKey);

    public Optional<DatalockerValues> getDatalockerValues(String key, String site);

}
