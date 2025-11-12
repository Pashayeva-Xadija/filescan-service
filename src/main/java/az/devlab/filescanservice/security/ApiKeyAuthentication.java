package az.devlab.filescanservice.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class ApiKeyAuthentication extends AbstractAuthenticationToken {

    private final String apiKey;

    public ApiKeyAuthentication(String apiKey, boolean authenticated) {
        super(null);
        this.apiKey = apiKey;
        setAuthenticated(authenticated);
    }

    @Override
    public Object getCredentials() {
        return apiKey; }
    @Override
    public Object getPrincipal()   {
        return "api-key-user"; }
}
