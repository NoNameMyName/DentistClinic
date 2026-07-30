package ffeks.smykov_rv.dentistclinic.reservation.service.impl;

import ffeks.smykov_rv.dentistclinic.reservation.model.Administrator;
import ffeks.smykov_rv.dentistclinic.reservation.repository.AdminRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AdminServiceImplTest {

    @Mock
    private AdminRepository repo;
    @InjectMocks
    private AdminServiceImpl service;

    @Test
    void findAdministratorById_success() {
        when(repo.findAdministratorById(1L)).thenReturn(Optional.of(new Administrator()));
        assertNotNull(service.findAdministratorById(1L));
    }

    @Test
    void findAdministratorById_notFound_shouldThrow() {
        when(repo.findAdministratorById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> service.findAdministratorById(1L));
    }

    @Test
    void findAdministratorByPhoneNumber_success() {
        when(repo.getAdministratorByUserAccountPhoneNumber("380")).thenReturn(Optional.of(new Administrator()));
        assertNotNull(service.findAdministratorByPhoneNumber("380"));
    }

    @Test
    void findAll_shouldReturnList() {
        when(repo.findAll()).thenReturn(List.of(new Administrator()));
        assertFalse(service.findAll().isEmpty());
    }

    @Test
    void findAdministratorByPhoneNumber_notFound_shouldThrow() {
        when(repo.getAdministratorByUserAccountPhoneNumber(anyString())).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> service.findAdministratorByPhoneNumber("111"));
    }
}