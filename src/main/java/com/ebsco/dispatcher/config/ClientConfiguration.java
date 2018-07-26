package com.ebsco.dispatcher.config;

import com.ebsco.dispatcher.model.Client;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@ConfigurationProperties(prefix = "clients")
@Configuration
public class ClientConfiguration {

    private List<Client> registeredClients;

    public List<Client> getRegisteredClients() {
        return registeredClients;
    }

    public void setRegisteredClients(List<Client> registeredClients) {
        this.registeredClients = registeredClients;
    }
}
