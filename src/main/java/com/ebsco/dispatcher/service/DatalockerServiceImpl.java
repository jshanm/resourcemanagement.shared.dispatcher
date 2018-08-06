package com.ebsco.dispatcher.service;

import com.ebsco.dispatcher.mocks.model.PUAUserInfo;

import java.util.Optional;

public class DatalockerServiceImpl implements DatalockerService {

    @Override
    public Optional<PUAUserInfo> getUserInfo(String datalockerKey) {
        return Optional.empty();
    }
}