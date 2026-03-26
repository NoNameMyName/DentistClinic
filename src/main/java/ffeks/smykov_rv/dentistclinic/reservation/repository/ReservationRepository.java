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

    @Query("""
        SELECT r 
        FROM Reservation r 
        JOIN r.location l
        join l.doctors d
        WHERE r.reservationDate = :date 
        AND l.id = :locationId
        AND d.id = :doctorId
        AND r.isCanceled =: status
        AND r.startTime BETWEEN :startTime AND :endTime 
    """)
    Optional<Reservation> findReservationByReservationDateTimeDoctorAndLocation(
            LocalDate date,
            LocalTime startTime,
            LocalTime endTime,
            Long doctorId,
            Long locationId,
            boolean status
    );
//    @Query("SELECT r FROM Reservation r WHERE r.reservationDate = :date AND r.startTime BETWEEN :startTime AND :endTime ")
//    boolean isExistReservationByDateStartAndEndTime(LocalDate date, LocalTime startTime, LocalTime endTime);
}
