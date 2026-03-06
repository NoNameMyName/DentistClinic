package ffeks.smykov_rv.dentistclinic.security.web;

import ffeks.smykov_rv.dentistclinic.security.mapper.RegisterRequestToUserAccountMapper;
import ffeks.smykov_rv.dentistclinic.security.service.UserAccountService;
import ffeks.smykov_rv.dentistclinic.security.usecase.RegisterUserAccountUseCase;
import ffeks.smykov_rv.dentistclinic.security.web.model.RegisterRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/accounts")
public class UserAccountController {

    private final RegisterUserAccountUseCase registerUserAccountUseCase;

    public UserAccountController(RegisterUserAccountUseCase registerUserAccountUseCase) {
        this.registerUserAccountUseCase = registerUserAccountUseCase;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerUserAccount(@Valid @RequestBody RegisterRequest registerRequest) {

        registerUserAccountUseCase.registerUserAccount(registerRequest);

    }
}
