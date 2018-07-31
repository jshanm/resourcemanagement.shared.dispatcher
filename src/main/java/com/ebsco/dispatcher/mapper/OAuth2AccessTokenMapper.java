package com.ebsco.dispatcher.mapper;

import com.amazonaws.services.dynamodbv2.document.Item;
//import com.ebsco.dispatcher.model.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;


import java.util.Optional;
import java.util.function.Function;

/**
 * @author jSHanmugam on 12/05/17.
 */
public interface OAuth2AccessTokenMapper {
    /**
     * @param item
     */
    Optional<OAuth2AccessToken> parse(Item item);


    /**
     * @param token
     * @return Function with Item that parameters
     */
    Function<OAuth2AccessToken, Item> parse(OAuth2AccessToken token, String state);
}
