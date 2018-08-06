package com.ebsco.dispatcher.token.scope;

import com.ebsco.dispatcher.mocks.AffiliationDTO;
import com.ebsco.dispatcher.mocks.model.PUAUserInfo;
import com.ebsco.dispatcher.service.DatalockerService;
import com.ebsco.dispatcher.util.DispatcherUtil;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

@Component
public class ContextManager {

    private final DatalockerService datalockerService;

    @Autowired
    public ContextManager(DatalockerService datalockerService) {
        super();
        this.datalockerService = datalockerService;
    }

    private ThreadLocal<OAuth2Authentication> authenticationThreadLocal;
    private ThreadLocal<PUAUserInfo> userInfoThreadLocal;
    private ThreadLocal<Set<AffiliationDTO>> affiliationsThreadLocal;

    public Optional<OAuth2Authentication> getAuthentication() {
        return Optional.ofNullable(this.getAuthenticationThreadLocal().get());
    }

    public Optional<PUAUserInfo> getDatalockerUserInformation() {
        return Optional.ofNullable(this.getUserInfoThreadLocal().get());
    }

    private ThreadLocal<OAuth2Authentication> getAuthenticationThreadLocal() {
        if (null == this.authenticationThreadLocal) {
            this.authenticationThreadLocal = new ThreadLocal<>();
        }
        return authenticationThreadLocal;
    }

    private ThreadLocal<PUAUserInfo> getUserInfoThreadLocal() {
        if (null == this.userInfoThreadLocal) {
            this.userInfoThreadLocal = new ThreadLocal<>();
        }
        return userInfoThreadLocal;
    }

    public void load(OAuth2Authentication authentication) {

        System.out.println("ContextManager.load: Seeding the EbscoContextManager for request...");

        this.getAuthenticationThreadLocal().set(authentication);
    }

    private ThreadLocal<Set<AffiliationDTO>> getAffiliationsThreadLocal() {
        if ((null == this.affiliationsThreadLocal) || (null == this.affiliationsThreadLocal.get())) {
            this.affiliationsThreadLocal = new ThreadLocal<>();
            this.affiliationsThreadLocal.set(Sets.newHashSet());
        }

        return affiliationsThreadLocal;
    }

    public void loadUserFromDatalocker(String datalockerKey) {

        //String principal = (String)authentication.getPrincipal();

        //LOGGER.debug("{} present loading UserInfo for [{}]", AppProperties.ROLE_PUA,  principal);

        Optional<PUAUserInfo> userInfoOptional = this.datalockerService.getUserInfo(datalockerKey);
        if (userInfoOptional.isPresent()) {
            PUAUserInfo info = userInfoOptional.get();
            this.getUserInfoThreadLocal().set(info);
            System.out.println("Successfully loaded UserInfo \n\t " + this.userInfoThreadLocal.get());

            if (info.getAffiliations() != null) {
                for (String affl : info.getAffiliations()) {
                    String[] afflArray = affl.split("\\.");
                    this.getAffiliationsThreadLocal().get().add(new AffiliationDTO("PUA", afflArray[0], afflArray[1]));
                    System.out.println("Successfully loaded stored Affilitaion(s) for PUA \n\t" + affl);
                }
            }

        }
    }

    public void loadAuthenticationFromRequest(String encodedAuthorizationContext) throws MalformedURLException, UnsupportedEncodingException {

        String authRequest = DispatcherUtil.base64Decode(encodedAuthorizationContext);

        Map<String, String> queryParameters = DispatcherUtil.splitQuery(new URL(authRequest));

        OAuth2Request oauth2Request = createOAuth2Request(queryParameters);

        Authentication authenticationObject = createAuthentication();

        OAuth2Authentication oAuth2Authentication  = new OAuth2Authentication(oauth2Request, authenticationObject);

        this.getAuthenticationThreadLocal().set(oAuth2Authentication);
    }

    private OAuth2Request createOAuth2Request(Map<String, String> queryParameters) { //TODO: Change this to private

        GrantedAuthority authority = new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return "authorization_code"; //TODO: See what to set here
            }
        };
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(authority);

        Set<String> scope = new HashSet<>();
        scope.add(queryParameters.get("scope")); //TODO: Make it a Set, not String

        Set<String> responseTypes = new HashSet<>();
        scope.add(queryParameters.get("response_type")); //TODO: Make it a Set, not String

        String redirectUri = queryParameters.get("redirect_uri"); //TODO: Default the redirect URI if not found in the request

        Map<String, Serializable> extensionProperties = new HashMap<>();
        extensionProperties.put("client_id", queryParameters.get("client_id")); //TODO: See whether we needede this.

        //TODO: Wire Client details Services and get client related information and use them below.

        OAuth2Request oAuth2Request =  new OAuth2Request(queryParameters, queryParameters.get("client_id"), authorities,  true, scope, null, redirectUri, responseTypes, extensionProperties);
        return oAuth2Request;
    }

    private Authentication createAuthentication() {

        Optional<PUAUserInfo> datalockerUserInformation = this.getDatalockerUserInformation();

        //Change this to private

        GrantedAuthority authority = new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return "authorization_code";
            }
        };
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(authority);

        return new Authentication() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return authorities; //TODO: See if this comes from datalocker
            }

            @Override
            public Object getCredentials() {
                return null; //TODO: See what information from datalocker should be passed here.
            }

            @Override
            public Object getDetails() {
                return datalockerUserInformation; //TODO: Verify
            }

            @Override
            public Object getPrincipal() {
                return datalockerUserInformation.get().getSub(); //TODO: See what to set here.
            }

            @Override
            public boolean isAuthenticated() {
                return true;
            }

            @Override
            public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

            }

            @Override
            public String getName() {
                return datalockerUserInformation.get().getGivenName();
            }
        };
    }
}
