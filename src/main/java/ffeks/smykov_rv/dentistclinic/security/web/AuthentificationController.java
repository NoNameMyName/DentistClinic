package ffeks.smykov_rv.dentistclinic.security.web;

import ffeks.smykov_rv.dentistclinic.security.usecase.AuthentificationUseCase;
import ffeks.smykov_rv.dentistclinic.security.web.model.AccessToken;
import ffeks.smykov_rv.dentistclinic.security.web.model.LoginRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/authentification")
public class AuthentificationController {

    private final AuthentificationUseCase authentificationUseCase;

    public AuthentificationController(AuthentificationUseCase authentificationUseCase) {
        this.authentificationUseCase = authentificationUseCase;
    }

    @PostMapping("/access_token")
    public AccessToken getToken(@Valid @RequestBody LoginRequest loginRequest) {
        return authentificationUseCase.authenticate(loginRequest);
    }
}
