package ffeks.smykov_rv.dentistclinic.reservation.service.impl;

import ffeks.smykov_rv.dentistclinic.reservation.model.Doctor;
import ffeks.smykov_rv.dentistclinic.reservation.model.Location;
import ffeks.smykov_rv.dentistclinic.reservation.repository.LocationsRepository;
import ffeks.smykov_rv.dentistclinic.reservation.service.AdminService;
import ffeks.smykov_rv.dentistclinic.reservation.service.DoctorService;
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
class LocationServiceImplTest {

    @Mock private DoctorService doctorService;
    @Mock
    private AdminService adminService;
    @Mock private LocationsRepository repo;

    @InjectMocks
    private LocationServiceImpl service;

    @Test
    void getAllLocations_shouldReturnList() {
        when(repo.findAllWithDoctorsAndAdministrators()).thenReturn(List.of(new Location()));
        assertEquals(1, service.getAllLocations().size());
    }

    @Test
    void isExistById_shouldReturnTrue() {
        when(repo.existsById(1L)).thenReturn(true);
        assertTrue(service.isExistById(1L));
    }

    @Test
    void getLocationById_shouldReturnOptional() {
        when(repo.findById(1L)).thenReturn(Optional.of(new Location()));
        assertTrue(service.getLocationById(1L).isPresent());
    }

    @Test
    void getDoctorByLocation_doctorExists_shouldReturn() {
        when(doctorService.existsDoctorById(10L)).thenReturn(true);
        when(repo.existsById(5L)).thenReturn(true);
        when(repo.findDoctorByLocation(10L, 5L)).thenReturn(Optional.of(new Doctor()));

        assertTrue(service.getDoctorByLocation(5L, 10L).isPresent());
    }

    @Test
    void getDoctorByLocation_notExists_shouldReturnEmpty() {
        when(doctorService.existsDoctorById(10L)).thenReturn(false);
        assertTrue(service.getDoctorByLocation(5L, 10L).isEmpty());
    }
}