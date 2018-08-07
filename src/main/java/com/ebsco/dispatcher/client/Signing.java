package com.ebsco.dispatcher.client;

public class Signing {
    private String defaultSignerKeyId;
    private String defaultSigningAlgorithmName;

    public String getDefaultSignerKeyId() {
        return defaultSignerKeyId;
    }

    public void setDefaultSignerKeyId(String defaultSignerKeyId) {
        this.defaultSignerKeyId = defaultSignerKeyId;
    }

    public String getDefaultSigningAlgorithmName() {
        return defaultSigningAlgorithmName;
    }

    public void setDefaultSigningAlgorithmName(String defaultSigningAlgorithmName) {
        this.defaultSigningAlgorithmName = defaultSigningAlgorithmName;
    }
}
