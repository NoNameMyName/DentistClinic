package ffeks.smykov_rv.dentistclinic.security.service.impl;

import ffeks.smykov_rv.dentistclinic.security.mapper.UserAccountToUserMapper;
import ffeks.smykov_rv.dentistclinic.security.model.UserAccount;
import ffeks.smykov_rv.dentistclinic.security.model.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class UserDetailsServiceImplUnitTest {
    @Mock
    UserAccountServiceImpl userAccountServiceImpl;

    @Mock
    UserAccountToUserMapper mapper;

    @InjectMocks
    UserDetailsServiceImpl userDetailsServiceImpl;

    private UserAccount userAccount;

    @BeforeEach
    void setUp() {
        userAccount = new UserAccount();
        userAccount.setPhoneNumber("123456789");
        userAccount.setPassword("password");
        userAccount.setAuthorities(Collections.singleton(new UserRole()));
    }

    @Test
    void shouldReturnUserDetails(){

        User expectedUser = new User(
                userAccount.getPhoneNumber(),
                userAccount.getPassword(),
                userAccount.getAuthorities()
        );
        Mockito.when(userAccountServiceImpl.getUserByPhone(userAccount.getPhoneNumber()))
                .thenReturn(Optional.of(userAccount));

        Mockito.when(mapper.map(userAccount))
                .thenReturn(expectedUser);

        UserDetails actualUser = userDetailsServiceImpl.loadUserByUsername(userAccount.getPhoneNumber());

        assertEquals(expectedUser, actualUser);

        Mockito.verify(userAccountServiceImpl, Mockito.times(1))
                .getUserByPhone(Mockito.any());
        Mockito.verify(mapper, Mockito.times(1))
                .map(Mockito.any());
    }

    @Test
    void shouldThrowUsernameNotFoundException(){

        Mockito.when(userAccountServiceImpl.getUserByPhone(userAccount.getPhoneNumber()))
                .thenReturn(Optional.empty());

        assertThrows(
                UsernameNotFoundException.class,
                () -> userDetailsServiceImpl.loadUserByUsername(userAccount.getPhoneNumber())
        );
        Mockito.verify(userAccountServiceImpl, Mockito.times(1))
                .getUserByPhone(Mockito.any());
        Mockito.verify(mapper, Mockito.never())
                .map(userAccount);
    }
}
