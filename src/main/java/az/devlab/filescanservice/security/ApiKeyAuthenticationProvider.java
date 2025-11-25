package az.devlab.filescanservice.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.security.authentication.BadCredentialsException;

@Component
public class ApiKeyAuthenticationProvider implements AuthenticationProvider {

    @Value("${security.api-key}")
    private String expectedKey;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String provided = (String) authentication.getCredentials();
        if (provided != null && provided.equals(expectedKey)) {
            return new ApiKeyAuthentication(provided, true);
        }
        throw new BadCredentialsException("Invalid API key");
    }

        @Override
    public boolean supports(Class<?> authentication) {
        return ApiKeyAuthentication.class.isAssignableFrom(authentication);
    }
}
