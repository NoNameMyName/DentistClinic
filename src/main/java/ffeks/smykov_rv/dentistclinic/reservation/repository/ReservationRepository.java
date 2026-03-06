package ffeks.smykov_rv.dentistclinic.reservation.repository;

import ffeks.smykov_rv.dentistclinic.reservation.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    List<Reservation> findReservationByReservationDate(LocalDate reservationDate);

    @Query("SELECT r FROM Reservation r WHERE r.reservationDate = :date AND r.startTime BETWEEN :startTime AND :endTime ")
    Optional<Reservation> findReservationByDateStartAndEndTime(LocalDate date, LocalTime startTime, LocalTime endTime);

//    @Query("SELECT r FROM Reservation r WHERE r.reservationDate = :date AND r.startTime BETWEEN :startTime AND :endTime ")
//    boolean isExistReservationByDateStartAndEndTime(LocalDate date, LocalTime startTime, LocalTime endTime);
}
