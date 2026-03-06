package ffeks.smykov_rv.dentistclinic.security.mapper.impl;

import ffeks.smykov_rv.dentistclinic.security.mapper.UserAccountToUserMapper;
import ffeks.smykov_rv.dentistclinic.security.model.UserAccount;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

@Component
public class UserAccountToUserMapperImpl implements UserAccountToUserMapper {
    @Override
    public User map(UserAccount userAccount) {
        return new User(
                        userAccount.getPhoneNumber(),
                        userAccount.getPassword(),
                        userAccount.getAuthorities());
    }
}
