package ffeks.smykov_rv.dentistclinic.security.web;

import ffeks.smykov_rv.dentistclinic.security.usecase.AuthentificationUseCase;
import ffeks.smykov_rv.dentistclinic.security.web.model.AccessToken;
import ffeks.smykov_rv.dentistclinic.security.web.model.LoginRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/authentification")
@Slf4j
public class AuthentificationController {

    private final AuthentificationUseCase authentificationUseCase;

    public AuthentificationController(AuthentificationUseCase authentificationUseCase) {
        this.authentificationUseCase = authentificationUseCase;
    }

    @PostMapping("/access_token")
    public AccessToken getToken(@Valid @RequestBody LoginRequest loginRequest) {
        return authentificationUseCase.authenticate(loginRequest);
    }

    @GetMapping("/role_info_for_user")
    public List<String> getUserRole(){
        Collection<? extends GrantedAuthority> authorities = Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getAuthorities().stream().toList();
        List<String> pureRoles = authorities.stream()
                .map(Object::toString)
                .filter(auth -> auth.startsWith("ROLE_"))
                .map(role -> role.substring(5))   // видаляємо "ROLE_"
                .collect(Collectors.toList());
        return pureRoles;
    }
}
