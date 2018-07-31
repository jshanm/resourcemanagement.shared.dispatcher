package com.ebsco.dispatcher.service;

import com.ebsco.dispatcher.model.UserInformation;

public interface DatalockerService {

    public UserInformation getUserInformation(String datalockeKey);

}
