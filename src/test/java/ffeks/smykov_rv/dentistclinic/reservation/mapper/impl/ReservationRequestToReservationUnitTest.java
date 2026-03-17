package ffeks.smykov_rv.dentistclinic.reservation.mapper.impl;

import ffeks.smykov_rv.dentistclinic.reservation.model.Administrator;
import ffeks.smykov_rv.dentistclinic.reservation.model.Doctor;
import ffeks.smykov_rv.dentistclinic.reservation.model.Reservation;
import ffeks.smykov_rv.dentistclinic.reservation.service.AdminService;
import ffeks.smykov_rv.dentistclinic.reservation.service.DoctorService;
import ffeks.smykov_rv.dentistclinic.reservation.service.ReservationService;
import ffeks.smykov_rv.dentistclinic.reservation.web.model.ReservationRequest;
import ffeks.smykov_rv.dentistclinic.security.model.UserAccount;
import ffeks.smykov_rv.dentistclinic.security.service.UserAccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationRequestToReservationUnitTest {
    @Mock
    ReservationService reservationService;

    @Mock
    UserAccountService userAccountService;

    @Mock
    DoctorService doctorService;

    @Mock
    AdminService adminService;

    @InjectMocks
    ReservationRequestToReservation reservationRequestToReservation;

    ReservationRequest reservationRequest;
    LocalDate ldn;
    LocalTime ltn;

    @BeforeEach
    void setUp() {
        ldn = LocalDate.now();
        ltn = LocalTime.now();
        reservationRequest = new ReservationRequest(
                "1",
                ldn,
                ltn,
                ltn.plusHours(1),
                1L,
                1L,
                2L
        );
    }

    @Test
    void shouldThrowException_whenReservationForDateAndTimeAlreadyTaken() {
        when(reservationService.isPresentReservationByReservationDateAndTime(
                reservationRequest.reservationDate(),
                reservationRequest.startTime(),
                reservationRequest.endTime()
        )).thenReturn(true);
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> reservationRequestToReservation.map(reservationRequest)
        );
        assertEquals("This time is already taken", exception.getMessage());
        verify(reservationService, Mockito.times(1))
                .isPresentReservationByReservationDateAndTime(
                    reservationRequest.reservationDate(),
                    reservationRequest.startTime(),
                    reservationRequest.endTime()
                );
        verifyNoMoreInteractions(reservationService);
        verifyNoMoreInteractions(userAccountService);
        verifyNoMoreInteractions(doctorService);
    }

    @Test
    void shouldThrowException_whenUserAccountDoesNotExist() {
        when(reservationService.isPresentReservationByReservationDateAndTime(
                reservationRequest.reservationDate(),
                reservationRequest.startTime(),
                reservationRequest.endTime()
        )).thenReturn(false);
        when(userAccountService.isExistById(reservationRequest.userAccount()))
                .thenReturn(false);
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> reservationRequestToReservation.map(reservationRequest)
        );
        assertEquals("This user account does not exist", exception.getMessage());
        verify(reservationService, Mockito.times(1))
                .isPresentReservationByReservationDateAndTime(
                    reservationRequest.reservationDate(),
                    reservationRequest.startTime(),
                    reservationRequest.endTime()
                );
        verify(userAccountService, Mockito.times(1))
                .isExistById(reservationRequest.userAccount());
        verifyNoMoreInteractions(reservationService);
        verifyNoMoreInteractions(userAccountService);
        verifyNoMoreInteractions(doctorService);
    }

    @Test
    void shouldThrowException_whenDoctorDoesNotExist() {
        when(reservationService.isPresentReservationByReservationDateAndTime(
                reservationRequest.reservationDate(),
                reservationRequest.startTime(),
                reservationRequest.endTime()
        )).thenReturn(false);
        when(userAccountService.isExistById(reservationRequest.userAccount()))
                .thenReturn(true);
        when(doctorService.existsDoctorById(reservationRequest.doctorId()))
            .thenReturn(false);
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> reservationRequestToReservation.map(reservationRequest)
        );
        assertEquals("This doctor account does not exist", exception.getMessage());
        verify(reservationService, Mockito.times(1))
                .isPresentReservationByReservationDateAndTime(
                    reservationRequest.reservationDate(),
                    reservationRequest.startTime(),
                    reservationRequest.endTime()
                );
        verify(userAccountService, Mockito.times(1))
                .isExistById(reservationRequest.userAccount());
        verify(doctorService, Mockito.times(1))
                .existsDoctorById(reservationRequest.doctorId());
        verifyNoMoreInteractions(reservationService);
        verifyNoMoreInteractions(userAccountService);
        verifyNoMoreInteractions(doctorService);
    }

    @Test
    void shouldReturnCorrectReservation() {



        Reservation reservation = new Reservation();
        reservation.setId(0);
        reservation.setDoctor(null);
        reservation.setAdministrator(null);
        reservation.setUserAccount(null);
        reservation.setReservationDate(ldn);
        reservation.setStartTime(ltn);
        reservation.setEndTime(ltn.plusHours(1));
        reservation.setReservationDescription("1");
        reservation.setAccepted(false);
        reservation.setCanceled(false);

        when(reservationService.isPresentReservationByReservationDateAndTime(
                reservationRequest.reservationDate(),
                reservationRequest.startTime(),
                reservationRequest.endTime()
        )).thenReturn(false);
        when(userAccountService.isExistById(reservationRequest.userAccount()))
                .thenReturn(true);
        when(doctorService.existsDoctorById(reservationRequest.doctorId()))
            .thenReturn(true);

        Reservation currentReservation = reservationRequestToReservation.map(reservationRequest);

        assertEquals(currentReservation, reservation);
        verify(reservationService, Mockito.times(1))
                .isPresentReservationByReservationDateAndTime(
                    reservationRequest.reservationDate(),
                    reservationRequest.startTime(),
                    reservationRequest.endTime()
                );
        verify(userAccountService, Mockito.times(1))
                .isExistById(reservationRequest.userAccount());
        verify(doctorService, Mockito.times(1))
                .existsDoctorById(reservationRequest.doctorId());
        verify(userAccountService, Mockito.times(1))
                .getUserById(reservationRequest.userAccount());
        verify(doctorService, Mockito.times(1))
                .findDoctorById(reservationRequest.doctorId());
        verify(adminService, Mockito.times(1))
                .findAdministratorById(reservationRequest.administratorId());
        verifyNoMoreInteractions(reservationService);
        verifyNoMoreInteractions(userAccountService);
        verifyNoMoreInteractions(adminService);
        verifyNoMoreInteractions(doctorService);
    }

}