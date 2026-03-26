package ffeks.smykov_rv.dentistclinic.reservation.mapper.impl;

import ffeks.smykov_rv.dentistclinic.reservation.mapper.Mapper;
import ffeks.smykov_rv.dentistclinic.reservation.model.Reservation;
import ffeks.smykov_rv.dentistclinic.reservation.service.AdminService;
import ffeks.smykov_rv.dentistclinic.reservation.service.DoctorService;
import ffeks.smykov_rv.dentistclinic.reservation.service.LocationsService;
import ffeks.smykov_rv.dentistclinic.reservation.service.ReservationService;
import ffeks.smykov_rv.dentistclinic.reservation.web.model.ReservationRequest;
import ffeks.smykov_rv.dentistclinic.security.service.UserAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Slf4j
@Component
public class ReservationRequestToReservation implements Mapper<Reservation, ReservationRequest> {

    private final ReservationService reservationService;
    private final UserAccountService userAccountService;
    private final LocationsService locationsService;

    public ReservationRequestToReservation(ReservationService reservationService, UserAccountService userAccountService, DoctorService doctorService, AdminService adminService, LocationsService locationsService) {
        this.reservationService = reservationService;
        this.userAccountService = userAccountService;
        this.locationsService = locationsService;
    }

    @Override
    public Reservation map(ReservationRequest reservationRequest) {

        if (!userAccountService.isExistById(reservationRequest.userAccount()))
        {
            throw new RuntimeException("This user account does not exist");
        }
        if (locationsService.getDoctorByLocation(
                reservationRequest.locationId() ,reservationRequest.doctorId()
        ).isEmpty())
        {
            throw new RuntimeException("This doctor account does not exist");
        }
        if (!locationsService.isExistById(reservationRequest.locationId())){
            throw new RuntimeException("This location does not exist");
        }

        boolean res = reservationService.isPresentReservationByReservationDateTimeDoctorAndLocation(
                reservationRequest.reservationDate(),
                reservationRequest.startTime(),
                reservationRequest.endTime(),
                reservationRequest.doctorId(),
                reservationRequest.locationId(),
                false

                );
        if(res)
        {
            throw new RuntimeException("This time is already taken");
        }


        Reservation reservation = new Reservation();
        reservation.setAccepted(false);
        reservation.setCanceled(false);
        reservation.setReservationDate(reservationRequest.reservationDate());
        reservation.setStartTime(reservationRequest.startTime());
        reservation.setEndTime(reservationRequest.endTime());
        reservation.setReservationDescription(reservationRequest.reservationDescription());

        reservation.setUserAccount(userAccountService.getUserById(reservationRequest.userAccount()));
        reservation.setDoctor(
                locationsService.getDoctorByLocation(
                        reservationRequest.locationId(),
                        reservationRequest.doctorId()
                ).get()
        );
        reservation.setLocation(locationsService.getLocationById(reservationRequest.locationId()).get());

        return reservation;
    }
}
