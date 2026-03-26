package ffeks.smykov_rv.dentistclinic.security.service.impl;

import ffeks.smykov_rv.dentistclinic.security.model.UserAccount;
import ffeks.smykov_rv.dentistclinic.security.model.UserRole;
import ffeks.smykov_rv.dentistclinic.security.repository.UserAccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserAccountServiceImplUnitTest {
    @Mock
    UserAccountRepository userAccountRepository;

    @InjectMocks
    UserAccountServiceImpl userAccountServiceImpl;

    private UserAccount userAccount;

    @BeforeEach
    void setUp() {
        userAccount = new UserAccount();
        userAccount.setId(1L);
        userAccount.setPhoneNumber("+380671234567");
        userAccount.setFirstName("FirstName");
        userAccount.setLastName("LastName");
        userAccount.setPassword("password");
        userAccount.setAuthorities(Collections.singleton(new UserRole()));
    }
    @Test
    void shouldCreateUserAccount_whenPhoneNumberIsUnique() {

        when(userAccountRepository.existsByPhoneNumber(anyString()))
                .thenReturn(false);

        userAccountServiceImpl
                .createUserAccount(userAccount);

        verify(userAccountRepository)
                .existsByPhoneNumber(userAccount.getPhoneNumber());

        verify(userAccountRepository).save(userAccount);

        verifyNoMoreInteractions(userAccountRepository);
    }

    @Test
    void shouldThrowException_whenPhoneNumberAlreadyExists() {

        when(userAccountRepository.existsByPhoneNumber(userAccount.getPhoneNumber()))
                .thenThrow(new RuntimeException());

        assertThrows(
                RuntimeException.class,
                () -> {userAccountServiceImpl.createUserAccount(userAccount);
        });

        verify(userAccountRepository)
                .existsByPhoneNumber(userAccount.getPhoneNumber());
        verify(userAccountRepository, never()).save(userAccount);
        verifyNoMoreInteractions(userAccountRepository);
    }
    @Test
    void shouldCallSaveWithTheSameObject() {
        when(userAccountRepository.existsByPhoneNumber(anyString())).thenReturn(false);

        userAccountServiceImpl.createUserAccount(userAccount);

        verify(userAccountRepository).save(same(userAccount));
    }

    @Test
    void shouldNotCallSave_whenPhoneExists() {
        when(userAccountRepository.existsByPhoneNumber(userAccount.getPhoneNumber()))
                .thenReturn(true);

        assertThrows(RuntimeException.class,
                () -> userAccountServiceImpl.createUserAccount(userAccount));

        verify(userAccountRepository, never()).save(any());
    }

    @Test
    void shouldReturnUserAccountByPhoneNumber() {
        when(userAccountServiceImpl.getUserByPhone(userAccount.getPhoneNumber()))
                .thenReturn(Optional.of(userAccount));

        assertEquals(userAccount, userAccountServiceImpl.getUserByPhone(userAccount.getPhoneNumber()).get());
        verify(userAccountRepository, Mockito.times(1))
                .findByPhoneNumber(userAccount.getPhoneNumber());

    }

    @Test
    void shouldReturnNullOptionOfUserAccountByPhoneNumber() {
        when(userAccountServiceImpl.getUserByPhone(userAccount.getPhoneNumber()))
                .thenReturn(Optional.empty());

        assertEquals(Optional.empty(), userAccountServiceImpl
                .getUserByPhone(userAccount.getPhoneNumber()));

        verify(userAccountRepository, Mockito.times(1))
                .findByPhoneNumber(userAccount.getPhoneNumber());
    }

    @Test
    void shouldReturnUserAccount_whenUserAccountExists() {

        Long id = userAccount.getId();

        when(userAccountRepository.existsById(id))
                .thenReturn(true);
        when(userAccountRepository.findById(id))
                .thenReturn(Optional.of(userAccount));
        UserAccount actualUser = userAccountServiceImpl.getUserById(id);

        assertEquals(userAccount, actualUser);

        verify(userAccountRepository, Mockito.times(1))
                .existsById(userAccount.getId());
        verify(userAccountRepository, Mockito.times(1))
                .findById(userAccount.getId());
        verifyNoMoreInteractions(userAccountRepository);
    }

    @Test
    void shouldThrowRuntimeException_whenUserAccountNotExists() {

        Long id = userAccount.getId();

        when(userAccountRepository.existsById(id))
                .thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> userAccountServiceImpl.getUserById(id));


        assertEquals("Account with this phone id does not exist",
                exception.getMessage());
        verify(userAccountRepository, Mockito.times(1))
                .existsById(id);

        verifyNoMoreInteractions(userAccountRepository);
    }

    @Test
    void shouldReturnTrue_whenUserAccountExistsById() {
        Long id = userAccount.getId();

        when(userAccountRepository.findById(id))
                .thenReturn(Optional.of(userAccount));

        boolean actualResponse = userAccountServiceImpl.isExistById(id);
        assertTrue(actualResponse);

        verify(userAccountRepository, Mockito.times(1))
                .findById(id);
        verifyNoMoreInteractions(userAccountRepository);
    }

    @Test
    void shouldReturnFalse_whenUserAccountExistsById() {
        Long id = userAccount.getId();

        when(userAccountRepository.findById(id))
                .thenReturn(Optional.empty());

        boolean actualResponse = userAccountServiceImpl.isExistById(id);
        assertFalse(actualResponse);

        verify(userAccountRepository, Mockito.times(1))
                .findById(id);
        verifyNoMoreInteractions(userAccountRepository);
    }
}