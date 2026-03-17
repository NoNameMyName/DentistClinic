package ffeks.smykov_rv.dentistclinic.reservation.service.impl;

import ffeks.smykov_rv.dentistclinic.reservation.model.Reservation;
import ffeks.smykov_rv.dentistclinic.reservation.repository.ReservationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationServiceImplUnitTest {

    @Mock
    ReservationRepository reservationRepository;
    @InjectMocks
    ReservationServiceImpl reservationServiceImpl;

    @Test
    void shouldFindAllReservations() {
        when(reservationRepository.findAll())
                .thenReturn(List.of(new Reservation()));


        List<Reservation> allReservations = reservationServiceImpl.allReservations();
        assertEquals(allReservations, List.of(new Reservation()));
        verify(reservationRepository, Mockito.times(1))
                .findAll();
        verifyNoMoreInteractions(reservationRepository);

    }

    @Test
    void shouldAddReservation() {
        Reservation reservation = new Reservation();
        reservationServiceImpl.addReservation(reservation);
        verify(reservationRepository, Mockito.times(1))
                .save(reservation);
        verifyNoMoreInteractions(reservationRepository);
    }

    @Test
    void shouldReturnTrue_whenReservationIsExistsByTimeAndDate(){
        Reservation reservation = new Reservation();
        when(reservationRepository.findReservationByDateStartAndEndTime(
                reservation.getReservationDate(),
                reservation.getStartTime(),
                reservation.getEndTime()))
                .thenReturn(Optional.of(reservation)
                );

        boolean res = reservationServiceImpl.isPresentReservationByReservationDateAndTime(
                reservation.getReservationDate(),
                reservation.getStartTime(),
                reservation.getEndTime()
        );

        assertTrue(res);
    }

    @Test
    void shouldReturnFalse_whenReservationIsExistsByTimeAndDate(){
        Reservation reservation = new Reservation();
        when(reservationRepository.findReservationByDateStartAndEndTime(
                reservation.getReservationDate(),
                reservation.getStartTime(),
                reservation.getEndTime()))
                .thenReturn(Optional.empty()
                );

        boolean res = reservationServiceImpl.isPresentReservationByReservationDateAndTime(
                reservation.getReservationDate(),
                reservation.getStartTime(),
                reservation.getEndTime()
        );

        assertFalse(res);
    }

}