package ffeks.smykov_rv.dentistclinic.reservation.service.impl;

import ffeks.smykov_rv.dentistclinic.reservation.buffer.impl.ReservationBufferImpl;
import ffeks.smykov_rv.dentistclinic.reservation.dto.ReservationMapping;
import ffeks.smykov_rv.dentistclinic.reservation.dto.mapping.ReservationDto;
import ffeks.smykov_rv.dentistclinic.reservation.model.Doctor;
import ffeks.smykov_rv.dentistclinic.reservation.model.Reservation;
import ffeks.smykov_rv.dentistclinic.reservation.repository.ReservationRepository;
import ffeks.smykov_rv.dentistclinic.reservation.service.DoctorService;
import ffeks.smykov_rv.dentistclinic.reservation.web.model.MakeReservationRequest;
import ffeks.smykov_rv.dentistclinic.security.model.UserAccount;
import ffeks.smykov_rv.dentistclinic.security.service.UserAccountService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationServiceImplTest {

    @Mock private ReservationRepository repository;
    @Mock private ReservationBufferImpl buffer;
    @Mock private DoctorService doctorService;
    @Mock private UserAccountService userAccountService;
    @Mock private ReservationMapping mapping;

    @InjectMocks
    private ReservationServiceImpl service;

    @Test
    void getAvailableSlots_sunday_shouldThrow() {
        assertThrows(IllegalArgumentException.class,
                () -> service.getAvailableSlots(1L, LocalDate.of(2026, 6, 7)));
    }

    @Test
    void getAvailableSlots_pastDate_shouldThrow() {
        assertThrows(IllegalArgumentException.class,
                () -> service.getAvailableSlots(1L, LocalDate.now().minusDays(1)));
    }

    @Test
    void makeReservation_success() {
        MakeReservationRequest req = new MakeReservationRequest(1L, LocalDate.now().plusDays(1), LocalTime.of(9,0), 30, "desc");

        when(buffer.tryBook(any(), any(), any(), anyInt())).thenReturn(true);
        when(doctorService.findDoctorById(anyLong())).thenReturn(new Doctor());
        when(userAccountService.getUserById(anyLong())).thenReturn(new UserAccount());
        when(repository.save(any())).thenReturn(new Reservation());
        when(mapping.toReservationDto(any())).thenReturn(new ReservationDto());

        assertDoesNotThrow(() -> service.makeReservation(req, 100L));
    }

    @Test
    void makeReservation_bufferFail_shouldThrow() {
        MakeReservationRequest req = new MakeReservationRequest(1L, LocalDate.now().plusDays(1), LocalTime.of(9,0), 30, "desc");
        when(buffer.tryBook(any(), any(), any(), anyInt())).thenReturn(false);

        assertThrows(RuntimeException.class, () -> service.makeReservation(req, 100L));
    }

    @Test
    void cancelReservation_wrongUser_shouldThrowAccessDenied() {
        Reservation res = new Reservation();
        UserAccount user = new UserAccount();
        user.setId(1L);
        res.setUserAccount(user);

        when(repository.findById(10L)).thenReturn(Optional.of(res));

        assertThrows(AccessDeniedException.class, () -> service.cancelReservation(10L, 999L));
    }

    @Test
    void cancelReservationByAdministrator_success() {
        Reservation res = new Reservation();
        res.setStartTime(LocalTime.of(10, 0));
        res.setEndTime(LocalTime.of(11, 0));
        res.setDoctor(new Doctor()); // потрібно для releaseSlot
        res.setReservationDate(LocalDate.now().plusDays(1));

        when(repository.findById(5L)).thenReturn(Optional.of(res));

        assertDoesNotThrow(() -> service.cancelReservationByAdministrator(5L));

        verify(repository).save(res);
        verify(buffer).releaseSlot(
                eq(res.getDoctor().getId()),
                eq(res.getReservationDate()),
                eq(res.getStartTime()),
                anyInt()
        );
    }

    @Test
    void cancelReservation_reservationNotFound_shouldThrow() {
        when(repository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.cancelReservationByAdministrator(999L));
    }

    @Test
    void getReservationsByUsername_shouldCallRepository() {
        when(repository.getReservationsByUserAccount("test")).thenReturn(List.of(new Reservation()));
        assertFalse(service.getReservationsByUsername("test").isEmpty());
    }
}