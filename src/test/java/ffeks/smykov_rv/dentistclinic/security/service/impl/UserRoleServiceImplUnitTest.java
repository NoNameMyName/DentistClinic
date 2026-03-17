package ffeks.smykov_rv.dentistclinic.security.service.impl;

import ffeks.smykov_rv.dentistclinic.security.model.UserRole;
import ffeks.smykov_rv.dentistclinic.security.repository.UserRoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserRoleServiceImplUnitTest {
    @Mock
    private UserRoleRepository userRoleRepository;
    @InjectMocks
    private UserRoleServiceImpl userRoleServiceImpl;

    @Test
    void
    shouldFindUserRoleByAuthority() {
        Optional<UserRole> actual = userRoleRepository.findByAuthority("ROLE_USER");
        Optional<UserRole> expectedRole = userRoleServiceImpl.findUserRole();
        assertEquals(expectedRole,actual);
        Mockito.verify(userRoleRepository,Mockito.times(2))
                .findByAuthority("ROLE_USER");
    }

}