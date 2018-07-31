package com.ebsco.dispatcher.mapper;

import com.amazonaws.services.dynamodbv2.document.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.stereotype.Component;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

import java.util.*;
import java.util.function.Function;

/**
 * @author gsilva-ebsco on 12/05/17.
 */
@ConditionalOnProperty(value="zuul.filter.authorization.enabled", matchIfMissing = false, havingValue = "true")
@Component
public class OAuth2AccessTokenMapperImpl implements OAuth2AccessTokenMapper {

    private final static Logger LOG = LoggerFactory.getLogger(OAuth2AccessTokenMapperImpl.class);

    /**
     * Fields of AccessToken
     */
    public enum Fields {

        SESSION_ID("session_id"),
        ACCESS_TOKEN("accessToken"),
        REFRESH_TOKEN("refreshToken"),
        ID_TOKEN("idToken"),
        EXPIRATION("expiration"),
        PROFILES("profiles"),
        TARGET_URL("target_url");

        private final String name;

        Fields(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    /**
     * @param item
     */
    @Override
    public Optional<OAuth2AccessToken> parse(Item item) {
        if (item == null) {
            LOG.info("The item is null, the parse return empty");
            return Optional.empty();
        }

        LOG.info("Parsing item to Optional<OAuth2AccessToken>");

        GrantedAuthority authority = new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return "authorization_code";
            }
        };
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(authority);

        Set<String> scope = new HashSet<>();
        scope.add("user_info");

        Set<String> responseTypes = new HashSet<>();
        scope.add("code");

        OAuth2AccessToken token = new org.springframework.security.oauth2.common.OAuth2AccessToken() {
            @Override
            public Map<String, Object> getAdditionalInformation() {
                return null;
            }

            @Override
            public Set<String> getScope() {
                return scope;
            }

            @Override
            public OAuth2RefreshToken getRefreshToken() {
                return null;
            }

            @Override
            public String getTokenType() {
                return null;
            }

            @Override
            public boolean isExpired() {
                return true;
            }

            @Override
            public Date getExpiration() {
                return null;
            }

            @Override
            public int getExpiresIn() {
                return 0;
            }

            @Override
            public String getValue() {
                return "THE_REAL_TOKEN";
            }
        };
        return Optional.of(token);
    }

	/**
	 * @param token
	 * @return Function with item as parameter
	 */
	@Override
	public Function<OAuth2AccessToken, Item> parse(OAuth2AccessToken token, String state) {
		if (token == null) {
			LOG.info("The OAuth2AccessToken is null, the return is new Item");
			return accessToken -> new Item();
		}
		LOG.info("Parsing AccessToken to Item");
		Item item = new Item().withString(Fields.ACCESS_TOKEN.getName(), token.getValue())
				.withString(Fields.ID_TOKEN.getName(), token.getValue())
				.withLong(Fields.EXPIRATION.getName(), token.getExpiresIn());
		return accessToken -> item;

	}
}
