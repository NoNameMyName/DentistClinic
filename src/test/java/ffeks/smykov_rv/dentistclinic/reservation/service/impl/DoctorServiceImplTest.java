package ffeks.smykov_rv.dentistclinic.reservation.service.impl;

import ffeks.smykov_rv.dentistclinic.reservation.model.Doctor;
import ffeks.smykov_rv.dentistclinic.reservation.repository.DoctorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DoctorServiceImplTest {

    @Mock private DoctorRepository repo;
    @InjectMocks
    private DoctorServiceImpl service;

    @Test
    void findDoctorById_success() {
        when(repo.findDoctorById(1L)).thenReturn(Optional.of(new Doctor()));
        assertNotNull(service.findDoctorById(1L));
    }

    @Test
    void findDoctorById_notFound_shouldThrow() {
        when(repo.findDoctorById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> service.findDoctorById(1L));
    }

    @Test
    void existsDoctorById_success() {
        when(repo.findDoctorById(1L)).thenReturn(Optional.of(new Doctor()));
        assertTrue(service.existsDoctorById(1L));
    }

    @Test
    void findDoctorByPhoneNumber_success() {
        when(repo.getDoctorByUserAccountPhoneNumber("123")).thenReturn(Optional.of(new Doctor()));
        assertNotNull(service.findDoctorByPhoneNumber("123"));
    }

    @Test
    void findAllDoctors_shouldReturnList() {
        when(repo.findAll()).thenReturn(List.of(new Doctor()));
        assertFalse(service.findAllDoctors().isEmpty());
    }
}