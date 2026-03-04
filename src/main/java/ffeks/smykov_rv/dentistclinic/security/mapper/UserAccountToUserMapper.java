package ffeks.smykov_rv.dentistclinic.security.mapper;

import ffeks.smykov_rv.dentistclinic.security.model.UserAccount;
import org.springframework.security.core.userdetails.User;

public interface UserAccountToUserMapper extends Mapper<User, UserAccount> {
}
