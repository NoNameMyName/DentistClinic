package ffeks.smykov_rv.dentistclinic.security.service.impl;

import ffeks.smykov_rv.dentistclinic.security.model.UserAccount;
import ffeks.smykov_rv.dentistclinic.security.repository.UserAccountRepository;
import ffeks.smykov_rv.dentistclinic.security.service.UserAccountService;
import org.springframework.stereotype.Service;

@Service
public class UserAccountServiceImpl implements UserAccountService {

    private final UserAccountRepository userAccountRepository;

    public UserAccountServiceImpl(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

    @Override
    public void createUserAccount(UserAccount userAccount) {

        boolean isExistsByPhoneNumber = userAccountRepository.existsByPhoneNumber(userAccount.getPhoneNumber());
        boolean isExistsByEmail = userAccountRepository.existsByEmail(userAccount.getEmail());

        if (isExistsByPhoneNumber) {
            throw new RuntimeException("Account with this phone number already exists");
        }

        if (isExistsByEmail) {
            throw new RuntimeException("Account with this email number already exists");
        }

        userAccountRepository.save(userAccount);
    }
}
