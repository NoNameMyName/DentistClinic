package ffeks.smykov_rv.dentistclinic.reservation.repository;

import ffeks.smykov_rv.dentistclinic.reservation.model.Doctor;
import ffeks.smykov_rv.dentistclinic.reservation.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LocationsRepository extends JpaRepository<Location, Long> {
    @Query("""
        SELECT d FROM Location l
        JOIN l.doctors d
        WHERE l.id = :locationId AND d.id = :doctorId
    """)
    Optional<Doctor> findDoctorByLocation(@Param("doctorId") Long doctorId,
                                          @Param("locationId") Long locationId);

    @Query("""
    SELECT DISTINCT l 
    FROM Location l 
    LEFT JOIN FETCH l.doctors d
    LEFT JOIN FETCH l.administrators a
""")
    List<Location> findAllWithDoctorsAndAdministrators();
}
