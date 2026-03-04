package ffeks.smykov_rv.dentistclinic.security.usecase;

import ffeks.smykov_rv.dentistclinic.security.web.model.RegisterRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;

public interface RegisterUserAccountUseCase {
    void registerUserAccount(RegisterRequest registerRequest);
}
