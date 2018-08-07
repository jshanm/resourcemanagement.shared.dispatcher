package com.ebsco.dispatcher.client;

import com.ebsco.dispatcher.client.OAuth2Client;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author jshanmugam
 */

@ConfigurationProperties(prefix = "clients")
@Configuration
public class ClientConfiguration {

    private List<InMemoryClient> registeredClients;

    public List<InMemoryClient> getRegisteredClients() {
        return registeredClients;
    }

    public void setRegisteredClients(List<InMemoryClient> registeredClients) {
        this.registeredClients = registeredClients;
    }

    public InMemoryClient loadClientById(String id) {
        return this.getRegisteredClients().stream()
                .filter(t -> t.getId().equals(id))
                .findAny()
                .orElse(null);
    }
}
