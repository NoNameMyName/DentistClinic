package ffeks.smykov_rv.dentistclinic.security.usecase.impl;

import ffeks.smykov_rv.dentistclinic.security.mapper.RegisterRequestToUserAccountMapper;
import ffeks.smykov_rv.dentistclinic.security.service.UserAccountService;
import ffeks.smykov_rv.dentistclinic.security.usecase.RegisterUserAccountUseCase;
import ffeks.smykov_rv.dentistclinic.security.web.model.RegisterRequest;
import org.springframework.stereotype.Component;

@Component
public class RegisterUserAccountUseCaseFacade implements RegisterUserAccountUseCase {

    private final RegisterRequestToUserAccountMapper mapper;
    private final UserAccountService userAccountService;

    public RegisterUserAccountUseCaseFacade(RegisterRequestToUserAccountMapper mapper, UserAccountService userAccountService) {
        this.mapper = mapper;
        this.userAccountService = userAccountService;
    }

    @Override
    public void registerUserAccount(RegisterRequest registerRequest) {
        userAccountService.createUserAccount(mapper.map(registerRequest));
    }
}
