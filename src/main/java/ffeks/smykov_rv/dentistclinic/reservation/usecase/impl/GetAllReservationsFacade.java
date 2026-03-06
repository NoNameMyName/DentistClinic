package ffeks.smykov_rv.dentistclinic.reservation.usecase.impl;

import ffeks.smykov_rv.dentistclinic.reservation.dto.mapping.ReservationDto;
import ffeks.smykov_rv.dentistclinic.reservation.dto.ReservationMapping;
import ffeks.smykov_rv.dentistclinic.reservation.service.ReservationService;
import ffeks.smykov_rv.dentistclinic.reservation.usecase.GetAllReservationsUseCase;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetAllReservationsFacade implements GetAllReservationsUseCase {

    private final ReservationMapping reservationMapping;
    private final ReservationService reservationService;

    public GetAllReservationsFacade(ReservationMapping reservationMapping, ReservationService reservationService) {
        this.reservationMapping = reservationMapping;
        this.reservationService = reservationService;
    }

    @Override
    public List<ReservationDto> getAllReservations() {
        return reservationService
                .allReservations()
                .stream()
                .map(reservationMapping::toReservationDto)
                .toList();
//        return reservations;
    }
}
