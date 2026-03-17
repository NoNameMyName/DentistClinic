package ffeks.smykov_rv.dentistclinic.reservation.mapper.impl;

import ffeks.smykov_rv.dentistclinic.reservation.mapper.Mapper;
import ffeks.smykov_rv.dentistclinic.reservation.model.Reservation;
import ffeks.smykov_rv.dentistclinic.reservation.service.AdminService;
import ffeks.smykov_rv.dentistclinic.reservation.service.DoctorService;
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
    private final DoctorService doctorService;
    private final AdminService adminService;

    public ReservationRequestToReservation(ReservationService reservationService, UserAccountService userAccountService, DoctorService doctorService, AdminService adminService) {
        this.reservationService = reservationService;
        this.userAccountService = userAccountService;
        this.doctorService = doctorService;
        this.adminService = adminService;
    }

    @Override
    public Reservation map(ReservationRequest reservationRequest) {

        boolean res = reservationService.isPresentReservationByReservationDateAndTime(
                reservationRequest.reservationDate(),
                reservationRequest.startTime(),
                reservationRequest.endTime());
        if(res)
        {
            throw new RuntimeException("This time is already taken");
        }
        if (!userAccountService.isExistById(reservationRequest.userAccount()))
        {
            throw new RuntimeException("This user account does not exist");
        }
        if (!doctorService.existsDoctorById(reservationRequest.doctorId()))
        {
            throw new RuntimeException("This doctor account does not exist");
        }


        Reservation reservation = new Reservation();
        reservation.setAccepted(false);
        reservation.setReservationDate(reservationRequest.reservationDate());
        reservation.setStartTime(reservationRequest.startTime());
        reservation.setEndTime(reservationRequest.endTime());
        reservation.setReservationDescription(reservationRequest.reservationDescription());

        reservation.setUserAccount(userAccountService.getUserById(reservationRequest.userAccount()));
        reservation.setDoctor(doctorService.findDoctorById(reservationRequest.doctorId()));
        reservation.setAdministrator(adminService.findAdministratorById(reservationRequest.administratorId()));

        return reservation;
    }
}
