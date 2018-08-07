package com.ebsco.dispatcher.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

import java.util.*;

//TODO: Utilize this class when we call the Auth0 server to get the client details.

/**
 * @author jshanmugam
 */

@Service
public class InMemoryClientService implements ClientDetailsService {

    private ClientConfiguration clientConfig;

    @Autowired
    public InMemoryClientService(ClientConfiguration clientConfig){
        this.clientConfig = clientConfig;
    }

    @Override
    public OAuth2Client loadClientByClientId(String clientId) throws ClientRegistrationException {

        Optional<InMemoryClient> client = Optional.of(this.clientConfig.loadClientById(clientId));

        OAuth2Client oAuth2ClientDetails = new OAuth2Client(client.get());

        return oAuth2ClientDetails;
    }

}



