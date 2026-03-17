package ffeks.smykov_rv.dentistclinic.security.usecase;

import ffeks.smykov_rv.dentistclinic.security.web.model.AccessToken;
import ffeks.smykov_rv.dentistclinic.security.web.model.LoginRequest;

public interface AuthentificationUseCase {
    AccessToken authenticate(LoginRequest loginRequest);
}
