package ffeks.smykov_rv.dentistclinic.reservation.service.impl;

import ffeks.smykov_rv.dentistclinic.reservation.model.Reservation;
import ffeks.smykov_rv.dentistclinic.reservation.repository.ReservationRepository;
import ffeks.smykov_rv.dentistclinic.reservation.service.ReservationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationServiceImpl(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Override
    public List<Reservation> allReservations() {
        return reservationRepository.findAll();
    }

    @Override
    public void addReservation(Reservation reservation) {
        reservationRepository.save(reservation);
    }

//    @Override
//    public boolean isPresentReservationByReservationDateAndTime(Reservation reservation) {
////        Optional<Reservation> res = reservationRepository.findReservationByDateStartAndEndeTime(
////                reservation.getReservationDate(),
////                reservation.getStartTime(),
////                reservation.getEndeTime());
////        return res.isPresent();
//        return true;
//    }

    @Override
    public boolean isPresentReservationByReservationDateAndTime(LocalDate date, LocalTime start, LocalTime end) {
        log.info("isPresentReservationByReservationDateAndTime: date: {}, start: {}, end: {}", date, start, end);
//        boolean res = reservationRepository.isExistReservationByDateStartAndEndTime(date, start, end);

        Optional<Reservation> res = reservationRepository.findReservationByDateStartAndEndTime(date, start, end);
        log.info("isPresentReservationByReservationDateAndTime: res: {}", res);
//        return res;
        return res.isPresent();
    }
}
