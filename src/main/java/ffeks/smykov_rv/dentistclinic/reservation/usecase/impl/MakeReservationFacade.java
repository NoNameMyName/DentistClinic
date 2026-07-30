package ffeks.smykov_rv.dentistclinic.reservation.usecase.impl;

import ffeks.smykov_rv.dentistclinic.reservation.service.ReservationService;
import ffeks.smykov_rv.dentistclinic.reservation.usecase.MakeReservationUseCase;
import ffeks.smykov_rv.dentistclinic.reservation.web.model.MakeReservationByAdminRequest;
import ffeks.smykov_rv.dentistclinic.reservation.web.model.MakeReservationRequest;
import ffeks.smykov_rv.dentistclinic.security.service.UserAccountService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class MakeReservationFacade implements MakeReservationUseCase {

    private final ReservationService reservationService;
    private final UserAccountService userAccountService;

    public MakeReservationFacade(ReservationService reservationService, UserAccountService userAccountService) {
        this.reservationService = reservationService;
        this.userAccountService = userAccountService;
    }

    @Override
    public void makeReservation(MakeReservationRequest makeReservationRequest) {
        Long userId = userAccountService.getUserByPhone(SecurityContextHolder.getContext().getAuthentication().getName()).get().getId();
        reservationService.makeReservation(makeReservationRequest, userId);
    }

    @Override
    public void makeReservationByAdministrator(MakeReservationByAdminRequest request) {
        MakeReservationRequest reservationRequest = new MakeReservationRequest(
                request.doctorId(),
                request.date(),
                request.startTime(),
                request.serviceDurationMinutes(),
                request.description()
        );
        if (request.userId() != null) {
            reservationService.makeReservation(reservationRequest, request.userId());
        }
        else reservationService.makeReservationWithoutUser(reservationRequest);
    }
}
