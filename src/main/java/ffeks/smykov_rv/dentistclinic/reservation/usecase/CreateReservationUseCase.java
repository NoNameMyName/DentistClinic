package ffeks.smykov_rv.dentistclinic.reservation.usecase;

import ffeks.smykov_rv.dentistclinic.reservation.model.Reservation;
import ffeks.smykov_rv.dentistclinic.reservation.web.model.ReservationRequest;

public interface CreateReservationUseCase {
    void createReservation(ReservationRequest reservationRequest);
}
