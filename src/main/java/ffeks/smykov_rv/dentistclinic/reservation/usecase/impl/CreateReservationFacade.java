package ffeks.smykov_rv.dentistclinic.reservation.usecase.impl;

import ffeks.smykov_rv.dentistclinic.reservation.mapper.impl.ReservationRequestToReservation;
import ffeks.smykov_rv.dentistclinic.reservation.model.Reservation;
import ffeks.smykov_rv.dentistclinic.reservation.service.ReservationService;
import ffeks.smykov_rv.dentistclinic.reservation.usecase.CreateReservationUseCase;
import ffeks.smykov_rv.dentistclinic.reservation.web.model.ReservationRequest;
import org.springframework.stereotype.Component;

@Component
public class CreateReservationFacade implements CreateReservationUseCase {

    private final ReservationRequestToReservation reservationRequestToReservation;
    private final ReservationService reservationService;

    public CreateReservationFacade(ReservationRequestToReservation reservationRequestToReservation, ReservationService reservationService) {
        this.reservationRequestToReservation = reservationRequestToReservation;
        this.reservationService = reservationService;
    }

    @Override
    public void createReservation(ReservationRequest reservationRequest) {
        reservationService.addReservation(reservationRequestToReservation.map(reservationRequest));
    }
}
