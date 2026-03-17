package ffeks.smykov_rv.dentistclinic.security.usecase.impl;

import ffeks.smykov_rv.dentistclinic.security.service.AccessTokenService;
import ffeks.smykov_rv.dentistclinic.security.usecase.AuthentificationUseCase;
import ffeks.smykov_rv.dentistclinic.security.web.model.AccessToken;
import ffeks.smykov_rv.dentistclinic.security.web.model.LoginRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class AuthentificationFacade implements AuthentificationUseCase {

    private final AuthenticationManager authenticationManager;
    private final AccessTokenService accessTokenService;

    public AuthentificationFacade(
            AuthenticationManager authenticationManager,
            AccessTokenService accessTokenService
    ) {
        this.authenticationManager = authenticationManager;
        this.accessTokenService = accessTokenService;
    }

    @Override
    public AccessToken authenticate(LoginRequest loginRequest) {

        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(
                loginRequest.phoneNumber(),
                loginRequest.password()
        );

        Authentication authentication = this.authenticationManager.authenticate(authenticationToken);

        String idToken = accessTokenService.generateIdToken(authentication);

        return new AccessToken(idToken);
    }
}
