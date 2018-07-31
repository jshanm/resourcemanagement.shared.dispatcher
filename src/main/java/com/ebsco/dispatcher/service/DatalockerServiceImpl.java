package com.ebsco.dispatcher.service;

import com.ebsco.dispatcher.model.UserInformation;

public class DatalockerServiceImpl implements DatalockerService {
    @Override
    public UserInformation getUserInformation(String datalockeKey) {
        //TODO: Implement Datalocker Webservice
        UserInformation user = new UserInformation();
        user.setPuaid("13434");
        user.setUserName("JShan");
        return user;
    }
}
