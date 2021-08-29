package com.proj.local.sample.config;
import com.proj.local.sample.Main;
import com.startinpoint.utils.DesEncrypter;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.AccessTokenProvider;
import org.springframework.security.oauth2.client.token.AccessTokenRequest;
import org.springframework.security.oauth2.client.token.DefaultAccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

@Configuration
@EnableOAuth2Client
@PropertySource({"classpath:application.properties"})
@ComponentScan(basePackages = {"com.proj.local"})
public class Oauth2ClientConfig
{
  @Autowired(required = false)
  ClientHttpRequestFactory clientHttpRequestFactory;
  @Value("${token_url}")
  String tokenUrl;
  @Value("${client_id}")
  String client_id;
  @Value("${client_secret}")
  String client_secret;
  @Value("${grant_type}")
  String grant_type;
  @Value("${scope}")
  String scope;
  
  @Bean
  @Primary
  public OAuth2ProtectedResourceDetails clientAccessOnly() throws NoSuchAlgorithmException {
    try {
   
      ClientCredentialsResourceDetails resource = new ClientCredentialsResourceDetails();
      resource.setAccessTokenUri(this.tokenUrl);
      resource.setClientId(this.client_id);
      resource.setClientSecret(this.client_secret);
      resource.setGrantType(this.grant_type);
      resource.setScope(getScopeList());
      return (OAuth2ProtectedResourceDetails)resource;
    } catch (Exception e) {
      logger.info(" : client_secret value error message : " + e.getMessage());
      return null;
    } 
  }

  
  @Bean(name = {"resttemplate"})
  @Qualifier("oauth_template")
  public OAuth2RestOperations restTemplate() throws NoSuchAlgorithmException {
    if (clientAccessOnly() != null) {
      OAuth2RestTemplate template = new OAuth2RestTemplate(clientAccessOnly(), (OAuth2ClientContext)new DefaultOAuth2ClientContext(
            (AccessTokenRequest)new DefaultAccessTokenRequest()));
      template.setRequestFactory(getClientHttpRequestFactory());
      template.setAccessTokenProvider(clientAccessTokenProvider());
      return (OAuth2RestOperations)template;
    } 
    return null;
  }

  
  private List<String> getScopeList() {
    List<String> scopeList = new ArrayList<>();
    String[] scopes = this.scope.split(" ");
    for (int i = 0; i < scopes.length; i++) {
      scopeList.add(scopes[i]);
    }
    return scopeList;
  }
  
  private ClientHttpRequestFactory getClientHttpRequestFactory() {
    if (this.clientHttpRequestFactory == null) {
      this.clientHttpRequestFactory = (ClientHttpRequestFactory)new SimpleClientHttpRequestFactory();
    }
    return this.clientHttpRequestFactory;
  }
  
  @Bean
  public AccessTokenProvider clientAccessTokenProvider() {
    ClientCredentialsAccessTokenProvider accessTokenProvider = new ClientCredentialsAccessTokenProvider();
    accessTokenProvider.setRequestFactory(getClientHttpRequestFactory());
    return (AccessTokenProvider)accessTokenProvider;
  }
  
  @Bean
  public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
    return new PropertySourcesPlaceholderConfigurer();
  }
}
