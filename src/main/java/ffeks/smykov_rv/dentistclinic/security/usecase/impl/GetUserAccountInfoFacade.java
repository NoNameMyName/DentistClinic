package ffeks.smykov_rv.dentistclinic.security.usecase.impl;

import ffeks.smykov_rv.dentistclinic.reservation.dto.UserAccountMapper;
import ffeks.smykov_rv.dentistclinic.reservation.dto.mapping.UserAccountDto;
import ffeks.smykov_rv.dentistclinic.security.model.UserAccount;
import ffeks.smykov_rv.dentistclinic.security.service.UserAccountService;
import ffeks.smykov_rv.dentistclinic.security.usecase.GetUserAccountInfoUseCase;
import ffeks.smykov_rv.dentistclinic.security.web.model.UserAccountInfoRequest;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class GetUserAccountInfoFacade implements GetUserAccountInfoUseCase {

    private final UserAccountMapper userAccountMapper;
    private final UserAccountService userAccountService;

    public GetUserAccountInfoFacade(UserAccountMapper userAccountMapper, UserAccountService userAccountService) {
        this.userAccountMapper = userAccountMapper;
        this.userAccountService = userAccountService;
    }

    @Override
    public UserAccountDto getUserAccountInfo() {
        Optional<UserAccount> userAccount = userAccountService.getUserAccountDtoByToken();
        if (userAccount.isPresent()) {
            return userAccountMapper.toUserAccountDto(userAccount.get());
        }
        else {
            throw new RuntimeException("UserAccount not found");
        }
    }

    @Override
    public UserAccountDto getUserAccountInfo(UserAccountInfoRequest request) {
        if (request.phoneNumber() != null) {
            Optional<UserAccount> userAccount = userAccountService.getUserByPhone(request.phoneNumber());
            if (userAccount.isPresent()) {
                return userAccountMapper.toUserAccountDto(userAccount.get());
            }
            else {
                throw new RuntimeException("UserAccount not found");
            }
        } else {
            Optional<UserAccount> userAccount = userAccountService.getUserAccountByFirstNameAndLastName(request.firstName(), request.lastName());
            if (userAccount.isPresent()) {
                return userAccountMapper.toUserAccountDto(userAccount.get());
            }
            else {
                throw new RuntimeException("UserAccount not found");
            }
        }
    }
}
