package com.ebsco.dispatcher.model;

import com.ebsco.dispatcher.util.DateBuilder;

import java.util.Optional;

/**
 * This model represents an AccessToken for an authenticated user
 * <p>
 * Usage:
 * <p>
 * new OAuth2AccessToken
 * .OAuth2AccessTokenBuilder("auth_code")
 * .setAccessToken("acceess_token")
 * .setIdToken("id_token")
 * .setExpiration(3600)
 * .build();
 *
 * @author aariano-ebsco
 */
public class OAuth2AccessToken {

      String authenticationCode;
      String accessToken;
      String idToken;
      long expiration;
      String profiles;
      String targetUrl;

    public OAuth2AccessToken(){}

    private OAuth2AccessToken(OAuth2AccessTokenBuilder builder) {
        this.authenticationCode = builder.authenticationCode;
        this.accessToken = builder.accessToken;
        this.idToken = builder.idToken;
        this.expiration = builder.expiration;
        this.profiles = builder.profiles;
        this.targetUrl = builder.targetUrl;
    }

    /**
     * Get accessToken value with type
     * Bearer 123456 (sample)
     *
     * @return String with type and accessToken
     */
    @Override
    public String toString() {
        String TOKEN_TYPE = "Bearer";
        return String.format("%s %s", TOKEN_TYPE, this.accessToken);
    }

    /**
     * Returns the Authentication code
     *
     * @return the authentication code
     */
    public String getAuthenticationCode() {
        return this.authenticationCode;
    }

    /**
     * Returns the Access Token
     *
     * @return the Access Token
     */
    public String getAccessToken() {
        return this.accessToken;
    }


    /**
     * Returns the Id Token
     *
     * @return the Id Token
     */
    public String getIdToken() {
        return this.idToken;
    }

    /**
     * Returns the Expiration in miliseconds
     *
     * @return the Expiration in miliseconds
     */
    public long getExpiration() {
        return this.expiration;
    }

    /**
     * verify if token was expired
     *
     * @return true if the token was expired
     */
    public boolean isExpired() {
        return DateBuilder.fromMillis(this.expiration).isBeforeNow();
    }

	public String getProfiles() {
		return profiles;
	}

	public void setProfiles(String profiles) {
		this.profiles = profiles;
	}

	/**
	 * @return the targetUrl
	 */
	public String getTargetUrl() {
		return targetUrl;
	}
	/**
     * Builds a new OAuth2AccessToken model
     *
     * @author aariano-ebsco
     */
    public static class OAuth2AccessTokenBuilder {
        public String targetUrl;
		private final String authenticationCode;
        private String accessToken;
        private String idToken;
        private long expiration;
		private String profiles;

        /**
         * Constructor that accepts Authentication Code
         *
         * @param authenticationCode the Authentication Code
         */
        public OAuth2AccessTokenBuilder(String authenticationCode) {
            this.authenticationCode = authenticationCode;
        }

        /**
         * Sets OAuth2AccessToken Access Token
         *
         * @param accessToken the Access Token
         * @return Builder instance with AccessToken set
         */
        public OAuth2AccessTokenBuilder setAccessToken(String accessToken) {
            this.accessToken = accessToken;
            return this;
        }

        /**
         * Sets OAuth2AccessToken Id Token
         *
         * @param idToken the Id Token
         * @return Builder instance with IdToken set
         */
        public OAuth2AccessTokenBuilder setIdToken(String idToken) {
            this.idToken = idToken;
            return this;
        }

        /**
         * Sets OAuth2AccessTokenBuilder expiration
         *
         * @param expiration the expiration in miliseconds
         * @return Builder instance with Expiration set
         */
        public OAuth2AccessTokenBuilder setExpiration(long expiration) {
            this.expiration = expiration;
            return this;
        }

		public OAuth2AccessTokenBuilder setProfiles(String profiles) {
			this.profiles = profiles;
			return this;
		}

		public OAuth2AccessTokenBuilder setTargetUrl(String targetUrl) {
			this.targetUrl = targetUrl;
			return this;
		}

        /**
         * Builds a new Optional of OAuth2AccessToken
         *
         * @return Optional of OAuth2AccessToken
         */
        public Optional<OAuth2AccessToken> build() {
            return Optional.of(new OAuth2AccessToken(this));
        }
    }

}
