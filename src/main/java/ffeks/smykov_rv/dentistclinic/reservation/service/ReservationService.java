package ffeks.smykov_rv.dentistclinic.reservation.service;

import ffeks.smykov_rv.dentistclinic.reservation.model.Location;
import ffeks.smykov_rv.dentistclinic.reservation.model.Reservation;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ReservationService {
    List<Reservation> allReservations();
    void addReservation(Reservation reservation);
//    boolean isPresentReservationByReservationDateAndTime(Reservation reservation);
    boolean isPresentReservationByReservationDateTimeDoctorAndLocation(
            LocalDate date,
            LocalTime start,
            LocalTime end,
            Long doctorId,
            Long locationId,
            boolean status
    );


}
