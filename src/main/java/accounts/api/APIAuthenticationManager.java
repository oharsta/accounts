package accounts.api;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.stream.Collectors;

public class APIAuthenticationManager implements AuthenticationManager {

    private APIUserConfiguration apiUserConfiguration;

    private PasswordEncoder passwordEncoder;

    public APIAuthenticationManager(APIUserConfiguration apiUserConfiguration, PasswordEncoder passwordEncoder) {
        this.apiUserConfiguration = apiUserConfiguration;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String name = String.class.cast(authentication.getPrincipal());
        Optional<APIUser> apiUserOptional = apiUserConfiguration.getApiUsers().stream()
            .filter(apiUser -> apiUser.getName().equals(name)).findFirst();
        return apiUserOptional
            .filter(apiUser -> passwordEncoder.matches(String.class.cast(authentication.getCredentials()), apiUser
                .getPassword()))
            .map(apiUser -> new UsernamePasswordAuthenticationToken(
                apiUser,
                authentication.getCredentials(),
                apiUser.getScopes().stream().map(scope -> new SimpleGrantedAuthority("ROLE_".concat(scope.name())))
                    .collect(Collectors.toList())))
            .orElseThrow(() -> new AuthenticationServiceException("Nope"));
    }
}
