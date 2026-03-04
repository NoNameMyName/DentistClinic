package ffeks.smykov_rv.dentistclinic.security.mapper.impl;


import ffeks.smykov_rv.dentistclinic.security.mapper.RegisterRequestToUserAccountMapper;
import ffeks.smykov_rv.dentistclinic.security.model.UserAccount;
import ffeks.smykov_rv.dentistclinic.security.model.UserRole;
import ffeks.smykov_rv.dentistclinic.security.service.UserRoleService;
import ffeks.smykov_rv.dentistclinic.security.web.model.RegisterRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class RegisterRequestToUserAccountImpl implements RegisterRequestToUserAccountMapper {

    private final UserRoleService userRoleService;
    private final PasswordEncoder passwordEncoder;

    public RegisterRequestToUserAccountImpl(UserRoleService userRoleService,
                                            PasswordEncoder passwordEncoder) {
        this.userRoleService = userRoleService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserAccount map(RegisterRequest registerRequest) {
        UserRole userRole = userRoleService
                .findUserRole()
                .orElseThrow(() -> new RuntimeException("User Role Not Found"));

        UserAccount userAccount = new UserAccount();
        userAccount.setFirstName(registerRequest.firstName());
        userAccount.setLastName(registerRequest.lastName());
        userAccount.setEmail(registerRequest.email());
        userAccount.setPassword(passwordEncoder.encode(registerRequest.password()));
        userAccount.setPhoneNumber(registerRequest.phoneNumber());
        userAccount.setAuthorities(Set.of(userRole));

        return userAccount;
    }
}
