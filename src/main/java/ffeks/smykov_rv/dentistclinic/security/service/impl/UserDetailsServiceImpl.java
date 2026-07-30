package ffeks.smykov_rv.dentistclinic.security.service.impl;

import ffeks.smykov_rv.dentistclinic.security.mapper.UserAccountToUserMapper;
import ffeks.smykov_rv.dentistclinic.security.service.UserAccountService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Transactional
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserAccountService userAccountService;
    private final UserAccountToUserMapper mapper;

    public UserDetailsServiceImpl(UserAccountService userAccountService, UserAccountToUserMapper mapper) {
        this.userAccountService = userAccountService;
        this.mapper = mapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.error("loadUserByUsername: {}", username);
        return userAccountService
                .getUserByPhone(username)
                .map(mapper::map)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
