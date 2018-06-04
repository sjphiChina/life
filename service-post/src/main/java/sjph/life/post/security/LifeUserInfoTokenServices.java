package sjph.life.post.security;

/*
 * Copyright 2012-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


//import java.util.Collections;
//import java.util.List;
//import java.util.Map;
//
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.springframework.boot.autoconfigure.security.oauth2.resource.AuthoritiesExtractor;
//import org.springframework.boot.autoconfigure.security.oauth2.resource.FixedAuthoritiesExtractor;
//import org.springframework.boot.autoconfigure.security.oauth2.resource.FixedPrincipalExtractor;
//import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.oauth2.client.OAuth2RestOperations;
//import org.springframework.security.oauth2.client.OAuth2RestTemplate;
//import org.springframework.security.oauth2.client.resource.BaseOAuth2ProtectedResourceDetails;
//import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
//import org.springframework.security.oauth2.common.OAuth2AccessToken;
//import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
//import org.springframework.security.oauth2.provider.OAuth2Authentication;
//import org.springframework.security.oauth2.provider.OAuth2Request;
//import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
//import org.springframework.util.Assert;

public class LifeUserInfoTokenServices{
//public class LifeUserInfoTokenServices implements ResourceServerTokenServices {
//
//    protected final Log logger = LogFactory.getLog(getClass());
//
//    private final String userInfoEndpointUrl;
//
//    private final String clientId;
//
//    private OAuth2RestOperations restTemplate;
//
//    private String tokenType = DefaultOAuth2AccessToken.BEARER_TYPE;
//
//    private AuthoritiesExtractor authoritiesExtractor = new FixedAuthoritiesExtractor();
//
//    private PrincipalExtractor principalExtractor = new FixedPrincipalExtractor();
//
//    public LifeUserInfoTokenServices(String userInfoEndpointUrl, String clientId) {
//        this.userInfoEndpointUrl = userInfoEndpointUrl;
//        this.clientId = clientId;
//    }
//
//    public void setTokenType(String tokenType) {
//        this.tokenType = tokenType;
//    }
//
//    public void setRestTemplate(OAuth2RestOperations restTemplate) {
//        this.restTemplate = restTemplate;
//    }
//
//    public void setAuthoritiesExtractor(AuthoritiesExtractor authoritiesExtractor) {
//        Assert.notNull(authoritiesExtractor, "AuthoritiesExtractor must not be null");
//        this.authoritiesExtractor = authoritiesExtractor;
//    }
//
//    public void setPrincipalExtractor(PrincipalExtractor principalExtractor) {
//        Assert.notNull(principalExtractor, "PrincipalExtractor must not be null");
//        this.principalExtractor = principalExtractor;
//    }
//
//    @Override
//    public OAuth2Authentication loadAuthentication(String accessToken)
//            throws AuthenticationException, InvalidTokenException {
//        logger.debug(">>>>>>>>>>>>>>>>accessToken: " + accessToken);
//        Map<String, Object> map = getMap(this.userInfoEndpointUrl, accessToken);
//        if (map.containsKey("error")) {
//            if (this.logger.isDebugEnabled()) {
//                this.logger.debug("userinfo returned error: " + map.get("error"));
//            }
//            throw new InvalidTokenException(accessToken);
//        }
//        return extractAuthentication(map);
//    }
//
//    private OAuth2Authentication extractAuthentication(Map<String, Object> map) {
//        Object principal = getPrincipal(map);
//        List<GrantedAuthority> authorities = this.authoritiesExtractor
//                .extractAuthorities(map);
//        OAuth2Request request = new OAuth2Request(null, this.clientId, null, true, null,
//                null, null, null, null);
//        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
//                principal, "N/A", authorities);
//        token.setDetails(map);
//        return new OAuth2Authentication(request, token);
//    }
//
//    /**
//     * Return the principal that should be used for the token. The default implementation
//     * delegates to the {@link PrincipalExtractor}.
//     * @param map the source map
//     * @return the principal or {@literal "unknown"}
//     */
//    protected Object getPrincipal(Map<String, Object> map) {
//        Object principal = this.principalExtractor.extractPrincipal(map);
//        return (principal == null ? "unknown" : principal);
//    }
//
//    @Override
//    public OAuth2AccessToken readAccessToken(String accessToken) {
//        throw new UnsupportedOperationException("Not supported: read access token");
//    }
//
//    @SuppressWarnings({ "unchecked" })
//    private Map<String, Object> getMap(String path, String accessToken) {
//        if (this.logger.isDebugEnabled()) {
//            this.logger.debug("Getting user info from: " + path);
//        }
//        try {
//            OAuth2RestOperations restTemplate = this.restTemplate;
//            if (restTemplate == null) {
//                BaseOAuth2ProtectedResourceDetails resource = new BaseOAuth2ProtectedResourceDetails();
//                resource.setClientId(this.clientId);
//                restTemplate = new OAuth2RestTemplate(resource);
//            }
//            OAuth2AccessToken existingToken = restTemplate.getOAuth2ClientContext()
//                    .getAccessToken();
//            if (existingToken == null || !accessToken.equals(existingToken.getValue())) {
//                DefaultOAuth2AccessToken token = new DefaultOAuth2AccessToken(
//                        accessToken);
//                token.setTokenType(this.tokenType);
//                restTemplate.getOAuth2ClientContext().setAccessToken(token);
//            }
//            //return restTemplate.getForEntity(path, Map.class).getBody();
//            
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.APPLICATION_JSON);
//            headers.set("Authorization", "bearer"+" " + accessToken);
//            HttpEntity<Object> entity = new HttpEntity<>(headers);
//            logger.info(">>>>>>>>>>>>>>entity: " + entity.toString());
//            
//            String email = "gsh1986@gmail.com";
//            ResponseEntity<Map> restExchange = restTemplate.exchange(
//                    "http://lifeuser/v1/user/authentication/{email}/", HttpMethod.GET, null,
//                    Map.class, email);
//            
//            
////            ResponseEntity<Map> restExchange = restTemplate.exchange(
////                    "http://lifeauthentication/auth/user", HttpMethod.GET, entity,Map.class);
//            logger.info(">>>>>>>>>>>>>>Returned body: "+restExchange.getBody());
//            return restExchange.getBody();
//            
//            
//        }
//        catch (Exception ex) {
//            this.logger.error("Could not fetch user details: " + ex.getClass() + ": "
//                    + ex.getMessage(), ex);
//            return Collections.<String, Object>singletonMap("error",
//                    "Could not fetch user details");
//        }
//    }

}

