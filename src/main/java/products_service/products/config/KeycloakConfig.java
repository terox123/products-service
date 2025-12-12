package products_service.products.config;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakConfig {

 @Bean
 public Keycloak keycloak(){
     return KeycloakBuilder.builder()
             .clientId("api-gateway")
             .clientSecret("h2i4OXRGXrqJ1GYauf6UVI2S4vx3AXpr")
             .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
             .realm("microservices")
             .serverUrl("http://localhost:8082")
             .build();
 }

}
